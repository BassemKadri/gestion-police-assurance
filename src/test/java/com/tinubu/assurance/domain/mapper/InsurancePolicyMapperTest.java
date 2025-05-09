package com.tinubu.assurance.domain.mapper;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class InsurancePolicyMapperTest {

    private final InsurancePolicyMapper mapper = Mappers.getMapper(InsurancePolicyMapper.class);

    @Test
    void Should_MapInsurancePolicyDomain_When_GivenInsurancePolicyCreateCommand() {
        // Given
        InsurancePolicyCreateCommand command = new InsurancePolicyCreateCommand(
                "Assurance Samsung",
                PolicyStatus.ACTIVE,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2026, 6, 1)
        );

        // When
        InsurancePolicy policy = mapper.fromInsurancePolicyCreateCommand(command);

        // Then
        assertThat(policy).isNotNull();
        assertThat(policy.getId()).isNull();
        assertThat(policy.getName()).isEqualTo(command.getName());
        assertThat(policy.getStatus()).isEqualTo(command.getStatus());
        assertThat(policy.getStartDate()).isEqualTo(command.getStartDate());
        assertThat(policy.getEndDate()).isEqualTo(command.getEndDate());
    }

    @Test
    void Should_MapInsurancePolicyDomain_When_GivenInsurancePolicyUpdateCommand() {
        // Given
        UUID id = UUID.randomUUID();

        InsurancePolicyUpdateCommand command = new InsurancePolicyUpdateCommand(
                id,
                "Nouveau nom",
                PolicyStatus.INACTIVE,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2026, 7, 1)
        );

        // When
        InsurancePolicy policy = mapper.fromInsurancePolicyUpdateCommand(command);

        // Then
        assertThat(policy).isNotNull();
        assertThat(policy.getId()).isEqualTo(id);
        assertThat(policy.getName()).isEqualTo(command.getName());
        assertThat(policy.getStatus()).isEqualTo(command.getStatus());
        assertThat(policy.getStartDate()).isEqualTo(command.getStartDate());
        assertThat(policy.getEndDate()).isEqualTo(command.getEndDate());
    }


    @Test
    void Should_updateInsurancePolicy_When_GivenNewValues() {
        // Given
        UUID id = UUID.randomUUID();
        InsurancePolicy oldPolicy = InsurancePolicy.builder()
                .id(id)
                .name("Ancienne Assurance samsung")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .creationDate(Instant.parse("2024-05-01T10:00:00Z"))
                .lastUpdate(Instant.parse("2024-05-01T10:00:00Z"))
                .build();

        // Nouvelle version
        InsurancePolicy newPolicy = InsurancePolicy.builder()
                .id(id)
                .name("Nouvelle Assurance samsung")
                .status(PolicyStatus.INACTIVE)
                .startDate(LocalDate.of(2025, 1, 3))
                .endDate(LocalDate.of(2025, 12, 18))
                .build();

        // When
        InsurancePolicy updated = InsurancePolicyMapper.INSTANCE.update(oldPolicy, newPolicy);

        // Then
        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getName()).isEqualTo(newPolicy.getName());
        assertThat(updated.getStatus()).isEqualTo(newPolicy.getStatus());
        assertThat(updated.getStartDate()).isEqualTo(newPolicy.getStartDate());
        assertThat(updated.getEndDate()).isEqualTo(newPolicy.getEndDate());
        assertThat(updated.getCreationDate()).isEqualTo(oldPolicy.getCreationDate());
        assertThat(updated.getLastUpdate()).isNotEqualTo(oldPolicy.getLastUpdate());
    }

}
