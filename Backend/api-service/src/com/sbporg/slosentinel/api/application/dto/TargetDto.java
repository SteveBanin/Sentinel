package com.sbporg.slosentinel.api.application.dto;

import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;
import com.sbporg.slosentinel.shared.domain.model.TargetType;

public record TargetDto(
    TargetId id,
    String name,
    TargetType type,
    String endpoint,
    long intervalSeconds
) {}