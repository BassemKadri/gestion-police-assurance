package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateRespDto;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsurancePolicyCreateRespDtoMapperTest {

    private final InsurancePolicyCreateRespDtoMapper mapper = Mappers.getMapper(InsurancePolicyCreateRespDtoMapper.class);

    @Test
    void Should_MapInsurancePolicyCreateRespDto_When_GivenInsurancePolicy() {
        // Given
        InsurancePolicy domain = InsurancePolicy.builder()
                .id(UUID.randomUUID())
                .name("Assurance Habitation")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(Instant.parse("2025-05-01T12:00:00Z"))
                .lastUpdate(Instant.parse("2025-05-02T15:30:00Z"))
                .build();

        // When
        InsurancePolicyCreateRespDto dto = mapper.fromInsurancePolicy(domain);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(domain.getId());
        assertThat(dto.getName()).isEqualTo(domain.getName());
        assertThat(dto.getStatus().name()).isEqualTo(domain.getStatus().name());
        assertThat(dto.getStartDate()).isEqualTo(domain.getStartDate());
        assertThat(dto.getEndDate()).isEqualTo(domain.getEndDate());
        assertThat(dto.getCreationDate()).isEqualTo(domain.getCreationDate());
        assertThat(dto.getLastUpdate()).isEqualTo(domain.getLastUpdate());
    }

}
