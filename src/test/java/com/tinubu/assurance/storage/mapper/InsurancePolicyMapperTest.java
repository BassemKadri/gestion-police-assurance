package com.tinubu.assurance.storage.mapper;

import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus;
import com.tinubu.assurance.infrastructure.storage.mapper.InsurancePolicyMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsurancePolicyMapperTest {

    private final InsurancePolicyMapper mapper = Mappers.getMapper(InsurancePolicyMapper.class);

    @Test
    void Should_MapInsurancePolicy_When_GivenInsurancePolicyEntity() {
        // Given
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        InsurancePolicyEntity entity = InsurancePolicyEntity.builder()
                .id(id)
                .name("Assurance Santé")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(now)
                .lastUpdate(now)
                .build();

        // When
        InsurancePolicy domain = mapper.fromInsurancePolicyEntity(entity);

        // Then
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(id);
        assertThat(domain.getName()).isEqualTo("Assurance Santé");
        assertThat(domain.getStatus().name()).isEqualTo(entity.getStatus().name()); // enum name match
        assertThat(domain.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(domain.getEndDate()).isEqualTo(LocalDate.of(2026, 6, 1));
        assertThat(domain.getCreationDate()).isEqualTo(now);
        assertThat(domain.getLastUpdate()).isEqualTo(now);
    }
}
