package com.sbporg.slosentinel.worker.application.port.outbound;

import com.sbporg.slosentinel.worker.domain.model.CheckResult;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;

import java.util.List;

public interface CheckResultRepositoryPort {
    void save(CheckResult result);
    List<CheckResult> findLatest(int limit);
    List<CheckResult> findLatestByTarget(TargetId targetId, int limit);
}