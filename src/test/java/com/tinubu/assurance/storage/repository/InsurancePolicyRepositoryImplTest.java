package com.tinubu.assurance.storage.repository;

import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import com.tinubu.assurance.infrastructure.storage.repository.InsurancePolicyEntityRepository;
import com.tinubu.assurance.infrastructure.storage.repository.InsurancePolicyRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsurancePolicyRepositoryImplTest {

    @Mock
    private InsurancePolicyEntityRepository entityRepository;

    private InsurancePolicyRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new InsurancePolicyRepositoryImpl(entityRepository);
    }

    @Test
    void ShouldPersistInsurancePolicy_When_GivenDomainInsurancePolicy() {
        // Given
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        InsurancePolicy domain = InsurancePolicy.builder()
                .id(id)
                .name("Assurance Auto")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(now)
                .lastUpdate(now)
                .build();

        InsurancePolicyEntity entity = InsurancePolicyEntity.builder()
                .id(id)
                .name(domain.getName())
                .status(com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus.ACTIVE)
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .creationDate(now)
                .lastUpdate(now)
                .build();

        when(entityRepository.save(any())).thenReturn(entity);

        // When
        InsurancePolicy result = repository.saveOrUpdate(domain);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(domain.getName());
        assertThat(result.getStatus()).isEqualTo(PolicyStatus.ACTIVE);

        verify(entityRepository, times(1)).save(any());
    }

    @Test
    void ShouldReturnInsurancePolicy_When_IdExist() {
        // Given
        UUID id = UUID.randomUUID();
        InsurancePolicyEntity entity = InsurancePolicyEntity.builder()
                .id(id)
                .name("Police samsung")
                .status(com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(Instant.now().minusSeconds(3600))
                .lastUpdate(Instant.now())
                .build();

        when(entityRepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        InsurancePolicy result = repository.get(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(entity.getName());
        verify(entityRepository).findById(id);
    }

    @Test
    void shouldThrowException_When_InsuranceNotFound() {
        // Given
        UUID id = UUID.randomUUID();
        when(entityRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> repository.get(id))
                .isInstanceOf(InsurancePolicyNotFoundException.class)
                .hasMessageContaining(id.toString());

        verify(entityRepository).findById(id);
    }

}
