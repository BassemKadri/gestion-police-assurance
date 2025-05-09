package com.tinubu.assurance.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Value
@EqualsAndHashCode
public class InsurancePolicy {

    UUID id;
    String name;
    PolicyStatus status;
    LocalDate startDate;
    LocalDate endDate;

    Instant creationDate;

    Instant lastUpdate;

    @Builder(toBuilder = true)
    public InsurancePolicy(UUID id,
                           String name,
                           PolicyStatus status,
                           LocalDate startDate,
                           LocalDate endDate,
                           Instant creationDate,
                           Instant lastUpdate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
    }
}
