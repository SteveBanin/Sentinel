package com.sbporg.slosentinel.api.application.request;

public record CreateTargetRequest(
        String name,
        String type,
        String endpoint,
        long intervalSeconds
) {}