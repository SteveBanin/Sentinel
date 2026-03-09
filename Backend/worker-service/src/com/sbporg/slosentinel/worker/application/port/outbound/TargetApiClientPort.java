package com.sbporg.slosentinel.worker.application.port.outbound;

import com.sbporg.slosentinel.worker.domain.model.TargetDefinition;

import java.util.List;

public interface TargetApiClientPort {
    List<TargetDefinition> fetchTargets();
}
