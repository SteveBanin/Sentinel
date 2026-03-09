package com.sbporg.slosentinel.api.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TargetJpaRepository extends JpaRepository<TargetJpaEntity, UUID> {}
