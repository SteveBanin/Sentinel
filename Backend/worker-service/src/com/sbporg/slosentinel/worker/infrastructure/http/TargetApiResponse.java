package com.sbporg.slosentinel.worker.infrastructure.http;

import java.util.UUID;

public record TargetApiResponse(
        UUID id,
        String name,
        String type,
        String endpoint,
        long intervalSeconds
) {}