package com.tinubu.assurance.application.service.impl;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import com.tinubu.assurance.domain.service.InsurancePolicyDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsurancePolicyServiceImplTest {

    @Mock
    private InsurancePolicyDomainService service;

    private InsurancePolicyApplicationServiceImpl insurancePolicyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        insurancePolicyService = new InsurancePolicyApplicationServiceImpl(service);
    }

    @Test
    void Should_CreateInsurancePolicy_When_GivenInsurancePolicyCreateCommand() {
        // Given
        InsurancePolicyCreateCommand command = new InsurancePolicyCreateCommand(
                "Assurance Habitation",

                PolicyStatus.ACTIVE,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2026, 6, 1)
        );

        InsurancePolicy mappedPolicy = InsurancePolicy.builder()
                .name(command.getName())
                .status(command.getStatus())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .build();

        InsurancePolicy savedPolicy = InsurancePolicy.builder()
                .name(mappedPolicy.getName())
                .status(mappedPolicy.getStatus())
                .startDate(mappedPolicy.getStartDate())
                .endDate(mappedPolicy.getEndDate())
                .build();

        when(service.createInsurancePolicy(command)).thenReturn(savedPolicy);

        // When
        InsurancePolicy result = insurancePolicyService.createInsurancePolicy(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(command.getName());
        verify(service, times(1)).createInsurancePolicy(command);
    }

    @Test
    void Should_UpdateInsurancePolicy_When_GivenInsurancePolicyUpdateCommand() {
        // Given
        UUID id = UUID.randomUUID();

        InsurancePolicyUpdateCommand command = new InsurancePolicyUpdateCommand(
                id,
                "Nouvelle Assurance",
                PolicyStatus.ACTIVE,
                LocalDate.of(2025, 6, 2),
                LocalDate.of(2026, 6, 1)
        );

        InsurancePolicy updatedPolicy = InsurancePolicy.builder()
                .id(id)
                .name(command.getName())
                .status(command.getStatus())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .build();

        when(service.updateInsurancePolicy(command)).thenReturn(updatedPolicy);

        // When
        InsurancePolicy result = insurancePolicyService.updateInsurancePolicy(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Nouvelle Assurance");
        assertThat(result.getStatus()).isEqualTo(PolicyStatus.ACTIVE);

        verify(service, times(1)).updateInsurancePolicy(command);
    }

}
