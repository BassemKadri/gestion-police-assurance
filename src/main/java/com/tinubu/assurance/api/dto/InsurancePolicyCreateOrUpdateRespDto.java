package com.tinubu.assurance.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePolicyCreateOrUpdateRespDto {
    UUID id;
    String name;
    PolicyStatusDto status;
    LocalDate startDate;
    LocalDate endDate;
    Instant creationDate;
    Instant lastUpdate;
}

