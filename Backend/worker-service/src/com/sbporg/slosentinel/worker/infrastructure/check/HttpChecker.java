package com.sbporg.slosentinel.worker.infrastructure.check;

import com.sbporg.slosentinel.worker.domain.model.CheckResult;
import com.sbporg.slosentinel.worker.domain.model.TargetDefinition;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@Component
public class HttpChecker {

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public CheckResult check(TargetDefinition target, long timeoutMs) {
        long start = System.nanoTime();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(target.endpoint()))
                    .GET()
                    .timeout(java.time.Duration.ofMillis(timeoutMs))
                    .build();

            HttpResponse<Void> resp = client.send(request, HttpResponse.BodyHandlers.discarding());
            long latencyMs = (System.nanoTime() - start) / 1_000_000;

            boolean ok = resp.statusCode() >= 200 && resp.statusCode() < 400;
            return new CheckResult(target.id(), target.type(), ok, latencyMs, resp.statusCode(), null, Instant.now());
        } catch (Exception ex) {
            long latencyMs = (System.nanoTime() - start) / 1_000_000;
            return new CheckResult(target.id(), target.type(), false, latencyMs, 0, ex.getMessage(), Instant.now());
        }
    }
}