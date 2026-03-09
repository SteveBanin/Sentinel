package com.sbporg.slosentinel.worker.presentation.rest;

import com.sbporg.slosentinel.worker.application.port.out.CheckResultRepositoryPort;
import com.sbporg.slosentinel.shared.domain.valueobject.TargetId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/results")
public class ResultsController {

    private final CheckResultRepositoryPort repo;

    public ResultsController(CheckResultRepositoryPort repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<?> latest(@RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(repo.findLatest(limit));
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<?> latestByTarget(@PathVariable UUID targetId,
                                           @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(repo.findLatestByTarget(new TargetId(targetId), limit));
    }
}