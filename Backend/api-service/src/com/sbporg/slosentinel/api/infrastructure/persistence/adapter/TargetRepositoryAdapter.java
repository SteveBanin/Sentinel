package com.sbporg.slosentinel.api.infrastructure.persistence.adapter;

import com.sbporg.slosentinel.api.application.port.outbound.TargetRepositoryPort;
import com.sbporg.slosentinel.api.domain.model.Target;
import com.sbporg.slosentinel.api.infrastructure.persistence.jpa.TargetJpaEntity;
import com.sbporg.slosentinel.api.infrastructure.persistence.jpa.TargetJpaRepository;
import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class TargetRepositoryAdapter implements TargetRepositoryPort {

    private final TargetJpaRepository repo;

    public TargetRepositoryAdapter(TargetJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Target save(Target target) {
        TargetJpaEntity entity = new TargetJpaEntity(
                target.id().value(),                 // TargetId -> UUID
                target.name(),
                target.type().name(),                // enum -> String
                target.endpoint(),
                target.interval().toSeconds()        // Duration -> long
        );

        repo.save(entity);
        return target;
    }

    @Override
    public List<Target> findAll() {
        return repo.findAll().stream()
                .map(e -> new Target(
                        new TargetId(e.getId()),                     // UUID -> TargetId
                        e.getName(),
                        TargetType.valueOf(e.getType()),             // String -> enum
                        e.getEndpoint(),
                        Duration.ofSeconds(e.getIntervalSeconds())   // long -> Duration
                ))
                .toList();
    }
}
