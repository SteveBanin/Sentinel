package com.sbporg.slosentinel.worker.infrastructure.persistence.adapter;

import com.sbporg.slosentinel.worker.application.port.out.CheckResultRepositoryPort;
import com.sbporg.slosentinel.worker.domain.model.CheckResult;
import com.sbporg.slosentinel.worker.infrastructure.persistence.jpa.CheckResultJpaEntity;
import com.sbporg.slosentinel.worker.infrastructure.persistence.jpa.CheckResultJpaRepository;
import com.sbporg.slosentinel.shared.domain.model.TargetType;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CheckResultRepositoryAdapter implements CheckResultRepositoryPort {

    private final CheckResultJpaRepository repo;

    public CheckResultRepositoryAdapter(CheckResultJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(CheckResult r) {
        repo.save(new CheckResultJpaEntity(
                UUID.randomUUID(),
                r.targetId().value(),
                r.type().name(),
                r.success(),
                r.latencyMs(),
                r.statusCode(),
                r.error(),
                r.timestamp()
        ));
    }

    @Override
    public List<CheckResult> findLatest(int limit) {
        List<CheckResultJpaEntity> rows = (limit <= 50)
                ? repo.findTop50ByOrderByTimestampDesc()
                : repo.findTop200ByOrderByTimestampDesc();

        return rows.stream().map(this::toDomain).toList();
    }

    @Override
    public List<CheckResult> findLatestByTarget(TargetId targetId, int limit) {
        List<CheckResultJpaEntity> rows = (limit <= 50)
                ? repo.findTop50ByTargetIdOrderByTimestampDesc(targetId.value())
                : repo.findTop200ByTargetIdOrderByTimestampDesc(targetId.value());

        return rows.stream().map(this::toDomain).toList();
    }

    private CheckResult toDomain(CheckResultJpaEntity e) {
        return new CheckResult(
                new TargetId(e.getTargetId()),
                TargetType.valueOf(e.getType()),
                e.isSuccess(),
                e.getLatencyMs(),
                e.getStatusCode(),
                e.getError(),
                e.getTimestamp()
        );
    }
}