package com.sbporg.slosentinel.worker.domain.model;

import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

import java.time.Instant;

public record CheckResult(
        TargetId targetId,
        TargetType type,
        boolean success,
        long latencyMs,
        int statusCode,
        String error,
        Instant timestamp
) {}