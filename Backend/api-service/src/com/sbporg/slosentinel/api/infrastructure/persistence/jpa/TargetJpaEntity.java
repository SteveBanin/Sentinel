package com.sbporg.slosentinel.api.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "targets")
public class TargetJpaEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false, length = 2048)
    private String endpoint;

    @Column(nullable = false)
    private long intervalSeconds;

    protected TargetJpaEntity() {}

    public TargetJpaEntity(UUID id, String name, String type, String endpoint, long intervalSeconds) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.endpoint = endpoint;
        this.intervalSeconds = intervalSeconds;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getEndpoint() { return endpoint; }
    public long getIntervalSeconds() { return intervalSeconds; }
}
