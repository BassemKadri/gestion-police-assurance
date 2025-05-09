package com.tinubu.assurance.storage.mapper;

import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import com.tinubu.assurance.infrastructure.storage.mapper.InsurancePolicyEntityMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsurancePolicyEntityMapperTest {

    private final InsurancePolicyEntityMapper mapper = Mappers.getMapper(InsurancePolicyEntityMapper.class);

    @Test
    void Should_MapInsurancePolicyEntity_When_GivenInsurancePolicy() {
        // Given
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        InsurancePolicy domain = InsurancePolicy.builder()
                .id(id)
                .name("Assurance Vie")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(now)
                .lastUpdate(now)
                .build();

        // When
        InsurancePolicyEntity entity = mapper.fromInsurancePolicy(domain);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getName()).isEqualTo("Assurance Vie");
        assertThat(entity.getStatus().name()).isEqualTo(PolicyStatus.ACTIVE.name()); // enum mapping
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2026, 6, 1));
        assertThat(entity.getCreationDate()).isEqualTo(now);
        assertThat(entity.getLastUpdate()).isEqualTo(now);
    }
}
