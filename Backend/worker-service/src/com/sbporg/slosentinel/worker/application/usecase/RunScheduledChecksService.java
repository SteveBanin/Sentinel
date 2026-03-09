package com.sbporg.slosentinel.worker.application.usecase;

import com.sbporg.slosentinel.worker.application.port.outbound.CheckResultRepositoryPort;
import com.sbporg.slosentinel.worker.application.port.outbound.TargetApiClientPort;
import com.sbporg.slosentinel.worker.domain.model.TargetDefinition;
import com.sbporg.slosentinel.worker.infrastructure.check.HttpChecker;
import com.sbporg.slosentinel.worker.infrastructure.config.SentinelApiProperties;
import com.sbporg.slosentinel.worker.infrastructure.config.WorkerProperties;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RunScheduledChecksService {

    private final TargetApiClientPort targetApiClient;
    private final HttpChecker httpChecker;
    private final SentinelApiProperties apiProps;
    private final WorkerProperties workerProps;
    private final TaskExecutor checkExecutor;
    private final MeterRegistry meterRegistry;
    private final CheckResultRepositoryPort checkResultRepositoryPort;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public RunScheduledChecksService(
            TargetApiClientPort targetApiClient,
            HttpChecker httpChecker,
            SentinelApiProperties apiProps,
            WorkerProperties workerProps,
            TaskExecutor checkExecutor,
            MeterRegistry meterRegistry,
            CheckResultRepositoryPort checkResultRepositoryPort
    ) {
        this.targetApiClient = targetApiClient;
        this.httpChecker = httpChecker;
        this.apiProps = apiProps;
        this.workerProps = workerProps;
        this.checkExecutor = checkExecutor;
        this.meterRegistry = meterRegistry;
        this.checkResultRepositoryPort = checkResultRepositoryPort;
    }

    @Scheduled(fixedDelayString = "${sentinel.worker.poll-interval-ms:10000}")
    public void runOnce() {
        // prevent overlapping runs
        if (!running.compareAndSet(false, true)) return;

        try {
            List<TargetDefinition> targets = targetApiClient.fetchTargets();

            System.out.println("Fetched targets: " + targets.size()); // <-- add here
            
            if (targets.isEmpty()) return;

            var futures = targets.stream()
                    .map(t -> CompletableFuture.supplyAsync(() -> {
                        // For now: only HTTP targets are checked via HttpChecker
                        var result = httpChecker.check(t, apiProps.timeoutMs());

                        meterRegistry.counter("sentinel_checks_total",
                                "type", result.type().name(),
                                "outcome", result.success() ? "success" : "fail"
                        ).increment();

                        meterRegistry.timer("sentinel_check_latency_ms",
                                "type", result.type().name()
                        ).record(result.latencyMs(), java.util.concurrent.TimeUnit.MILLISECONDS);
                        checkResultRepositoryPort.save(result);
                        return result;
                    }, runnable -> checkExecutor.execute(runnable)))
                    .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } finally {
            running.set(false);
        }
    }
}