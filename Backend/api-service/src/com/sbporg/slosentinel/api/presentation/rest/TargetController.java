package com.sbporg.slosentinel.api.presentation.rest;

import com.sbporg.slosentinel.api.application.port.in.CreateTargetUseCase;
import com.sbporg.slosentinel.api.application.port.in.ListTargetsUseCase;
import com.sbporg.slosentinel.api.presentation.request.CreateTargetRequest;
import com.sbporg.slosentinel.api.presentation.response.TargetResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    private final CreateTargetUseCase createTargetUseCase;
    private final ListTargetsUseCase listTargetsUseCase;

    public TargetController(CreateTargetUseCase createTargetUseCase,
                            ListTargetsUseCase listTargetsUseCase) {
        this.createTargetUseCase = createTargetUseCase;
        this.listTargetsUseCase = listTargetsUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateTargetRequest request) {
        var cmd = new CreateTargetUseCase.CreateTargetCommand(
                request.name(),
                request.type(),
                request.endpoint(),
                request.intervalSeconds()
        );

        var id = createTargetUseCase.create(cmd); // TargetId

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.value()) // UUID in URL
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<TargetResponse>> list() {
        var targets = listTargetsUseCase.listAll().stream()
                .map(t -> new TargetResponse(
                        t.id().value(),          // TargetId -> UUID
                        t.name(),
                        t.type().name(),         // enum -> String
                        t.endpoint(),
                        t.intervalSeconds()
                ))
                .toList();

        return ResponseEntity.ok(targets);
    }
}