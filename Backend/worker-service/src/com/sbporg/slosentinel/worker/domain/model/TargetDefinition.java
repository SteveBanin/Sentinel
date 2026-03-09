package com.sbporg.slosentinel.worker.domain.model;

import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

import java.time.Duration;

public record TargetDefinition(
        TargetId id,
        String name,
        TargetType type,
        String endpoint,
        Duration interval
) {}