package com.sbporg.slosentinel.api.application.port.outbound;

import com.sbporg.slosentinel.api.domain.model.Target;

import java.util.List;

public interface TargetRepositoryPort {
    Target save(Target target);
    List<Target> findAll();
}