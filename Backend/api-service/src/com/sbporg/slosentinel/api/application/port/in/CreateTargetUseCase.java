package com.sbporg.slosentinel.api.application.port.in;

import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

public interface CreateTargetUseCase {

    TargetId create(CreateTargetCommand command);

    record CreateTargetCommand(
        String name,
        String type,
        String endpoint,
        long intervalSeconds
    ) {}
}