package com.sbporg.slosentinel.shared.domain.valueobject;

import java.util.UUID;

public record TargetId(UUID value) {
    public static TargetId newId() {
        return new TargetId(UUID.randomUUID());
    }
}