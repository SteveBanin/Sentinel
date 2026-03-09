package com.sbporg.slosentinel.worker.infrastructure.http;

import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;
import com.sbporg.slosentinel.worker.application.port.out.TargetApiClientPort;
import com.sbporg.slosentinel.worker.domain.model.TargetDefinition;
import com.sbporg.slosentinel.worker.infrastructure.config.SentinelApiProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@Component
public class WebClientTargetApiClient implements TargetApiClientPort {

    private final WebClient webClient;
    private final SentinelApiProperties props;

    public WebClientTargetApiClient(WebClient targetsWebClient, SentinelApiProperties props) {
        this.webClient = targetsWebClient;
        this.props = props;
    }

    @Override
    public List<TargetDefinition> fetchTargets() {
        List<TargetApiResponse> response = webClient.get()
                .uri("/api/targets")
                .retrieve()
                .bodyToFlux(TargetApiResponse.class)
                .timeout(Duration.ofMillis(props.timeoutMs()))
                .retryWhen(Retry.backoff(props.retries(), Duration.ofMillis(200))
                        .maxBackoff(Duration.ofSeconds(2)))
                .collectList()
                .block(Duration.ofMillis(props.timeoutMs() + 500));

        if (response == null) return List.of();

        return response.stream().map(r -> new TargetDefinition(
                new TargetId(r.id()),
                r.name(),
                TargetType.valueOf(r.type().trim().toUpperCase(Locale.ROOT)),
                r.endpoint(),
                Duration.ofSeconds(r.intervalSeconds())
        )).toList();
    }
}