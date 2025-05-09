package com.tinubu.assurance.infrastructure.storage.entity;

import com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INSURANCE_POLICY")
public class InsurancePolicyEntity {

    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, updatable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private Instant lastUpdate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = this.lastUpdate = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = Instant.now();
    }
}
