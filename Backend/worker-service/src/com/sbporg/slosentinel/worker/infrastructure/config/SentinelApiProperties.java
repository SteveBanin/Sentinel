package com.sbporg.slosentinel.worker.infrastructure.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "sentinel.api")
public record SentinelApiProperties(
        @NotBlank String baseUrl,
        long timeoutMs,
        int retries
) {}