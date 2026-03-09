package com.sbporg.slosentinel.api.application.port.in;


import com.sbporg.slosentinel.api.application.dto.TargetDto;
import java.util.List;

public interface ListTargetsUseCase {
    List<TargetDto> listAll();
}
