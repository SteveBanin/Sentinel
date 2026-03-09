package com.sbporg.slosentinel.worker.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CheckResultJpaRepository extends JpaRepository<CheckResultJpaEntity, UUID> {
    List<CheckResultJpaEntity> findTop50ByOrderByTimestampDesc();
    List<CheckResultJpaEntity> findTop200ByOrderByTimestampDesc();

    List<CheckResultJpaEntity> findTop50ByTargetIdOrderByTimestampDesc(UUID targetId);
    List<CheckResultJpaEntity> findTop200ByTargetIdOrderByTimestampDesc(UUID targetId);
}