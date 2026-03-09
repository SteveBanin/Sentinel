package com.sbporg.slosentinel.worker.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sentinel.worker")
public record WorkerProperties(
        long pollIntervalMs,
        int maxConcurrency
) {}