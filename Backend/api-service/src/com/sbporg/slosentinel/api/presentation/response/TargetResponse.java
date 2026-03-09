package com.sbporg.slosentinel.api.presentation.response;

import java.util.UUID;

public record TargetResponse(
        UUID id,
        String name,
        String type,
        String endpoint,
        long intervalSeconds
) {}