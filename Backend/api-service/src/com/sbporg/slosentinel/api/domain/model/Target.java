package com.sbporg.slosentinel.api.domain.model;

import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

import java.time.Duration;



public record Target(
        TargetId id,
        String name,
        TargetType type,  // keep simple for now: "HTTP"|"TCP"|"DNS"
        String endpoint,
        Duration interval
) {}

       