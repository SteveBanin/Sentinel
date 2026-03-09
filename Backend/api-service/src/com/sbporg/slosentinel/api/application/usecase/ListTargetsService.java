package com.sbporg.slosentinel.api.application.usecase;

import com.sbporg.slosentinel.api.application.dto.TargetDto;
import com.sbporg.slosentinel.api.application.port.in.ListTargetsUseCase;
import com.sbporg.slosentinel.api.application.port.outbound.TargetRepositoryPort;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListTargetsService implements ListTargetsUseCase {

  private final TargetRepositoryPort targetRepository;

  public ListTargetsService(TargetRepositoryPort targetRepository) {
      this.targetRepository = targetRepository;
  }

  @Override
  public List<TargetDto> listAll() {
      return targetRepository.findAll().stream()
          .map(t -> new TargetDto(
              t.id(),
              t.name(),
              t.type(),
              t.endpoint(),
              t.interval().toSeconds()
          ))
          .toList();
  }
}
