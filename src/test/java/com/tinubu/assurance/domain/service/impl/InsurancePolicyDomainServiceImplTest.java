package com.tinubu.assurance.domain.service.impl;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.mapper.InsurancePolicyMapper;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import com.tinubu.assurance.domain.repository.InsurancePolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsurancePolicyDomainServiceImplTest {

    @Mock
    private InsurancePolicyRepository insurancePolicyRepository;

    private InsurancePolicyDomainServiceImpl insurancePolicyDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        insurancePolicyDomainService = new InsurancePolicyDomainServiceImpl(insurancePolicyRepository);
    }

    @Test
    void Should_CreateInsurancePolicyInDomain_When_GivenInsurancePolicyCreateCommand() {
        // Given
        InsurancePolicyCreateCommand command = new InsurancePolicyCreateCommand(
                "Assurance Samsung",
                PolicyStatus.ACTIVE,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2026, 6, 1)
        );

        InsurancePolicy domainObject = InsurancePolicy.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .status(command.getStatus())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .creationDate(Instant.now())
                .lastUpdate(Instant.now())
                .build();

        when(insurancePolicyRepository.saveOrUpdate(any())).thenReturn(domainObject);

        // When
        InsurancePolicy result = insurancePolicyDomainService.createInsurancePolicy(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Assurance Samsung");
        verify(insurancePolicyRepository, times(1)).saveOrUpdate(any());
    }

    @Test
    void Should_UpdateInsurancePolicyInDomain_When_GivenUpdateCommand() {
        // Given
        UUID id = UUID.randomUUID();
        InsurancePolicyUpdateCommand command = new InsurancePolicyUpdateCommand(
                id,
                "Assurance Samsung updated",
                PolicyStatus.ACTIVE,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2026, 7, 1)
        );

        InsurancePolicy oldPolicy = InsurancePolicy.builder()
                .id(id)
                .name("Assurance Initiale")
                .status(PolicyStatus.INACTIVE)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .creationDate(Instant.parse("2024-01-01T00:00:00Z"))
                .lastUpdate(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        InsurancePolicy newPolicy = InsurancePolicyMapper.INSTANCE.fromInsurancePolicyUpdateCommand(command);
        InsurancePolicy mergedPolicy = InsurancePolicyMapper.INSTANCE.update(oldPolicy, newPolicy);

        when(insurancePolicyRepository.get(id)).thenReturn(oldPolicy);
        when(insurancePolicyRepository.saveOrUpdate(any())).thenReturn(mergedPolicy);

        // When
        InsurancePolicy result = insurancePolicyDomainService.updateInsurancePolicy(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Assurance Samsung updated");
        assertThat(result.getStatus()).isEqualTo(PolicyStatus.ACTIVE);
        assertThat(result.getStartDate()).isEqualTo(command.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(command.getEndDate());
        verify(insurancePolicyRepository).saveOrUpdate(any());
    }

}
