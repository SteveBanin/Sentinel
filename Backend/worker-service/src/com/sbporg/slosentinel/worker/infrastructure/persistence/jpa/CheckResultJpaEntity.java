package com.sbporg.slosentinel.worker.infrastructure.persistence.jpa;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "check_results", indexes = {
        @Index(name = "idx_check_results_target_ts", columnList = "targetId,timestamp")
})
public class CheckResultJpaEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID targetId;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private long latencyMs;

    @Column(nullable = false)
    private int statusCode;

    @Column(length = 2048)
    private String error;

    @Column(nullable = false)
    private Instant timestamp;

    protected CheckResultJpaEntity() {}

    public CheckResultJpaEntity(UUID id, UUID targetId, String type, boolean success,
                                long latencyMs, int statusCode, String error, Instant timestamp) {
        this.id = id;
        this.targetId = targetId;
        this.type = type;
        this.success = success;
        this.latencyMs = latencyMs;
        this.statusCode = statusCode;
        this.error = error;
        this.timestamp = timestamp;
    }

    public UUID getId() { return id; }
    public UUID getTargetId() { return targetId; }
    public String getType() { return type; }
    public boolean isSuccess() { return success; }
    public long getLatencyMs() { return latencyMs; }
    public int getStatusCode() { return statusCode; }
    public String getError() { return error; }
    public Instant getTimestamp() { return timestamp; }
}