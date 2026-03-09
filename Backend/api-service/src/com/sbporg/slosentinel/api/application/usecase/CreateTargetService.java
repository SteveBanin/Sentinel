package com.sbporg.slosentinel.api.application.usecase;

import com.sbporg.slosentinel.api.application.port.in.CreateTargetUseCase;
import com.sbporg.slosentinel.api.application.port.out.TargetRepositoryPort;
import com.sbporg.slosentinel.api.domain.model.Target;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Locale;


import com.sbporg.slosentinel.shared.domain.model.TargetType;


@Service
public class CreateTargetService implements CreateTargetUseCase {

    private final TargetRepositoryPort targetRepository;

    public CreateTargetService(TargetRepositoryPort targetRepository) {
        this.targetRepository = targetRepository;
    }

    @Override
    public TargetId create(CreateTargetCommand command) {
        var type = TargetType.valueOf(command.type().trim().toUpperCase(Locale.ROOT));
        if (command.intervalSeconds() < 5) {
            throw new IllegalArgumentException("intervalSeconds must be at least 5 seconds");
        }

        TargetId id = TargetId.newId();

        Target target = new Target(
                id,
                command.name().trim(),
                type,
                command.endpoint().trim(),
                Duration.ofSeconds(command.intervalSeconds())
        );

        targetRepository.save(target);
        return id;
    }

}