package com.tinubu.assurance.api.mapper;


import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;
import com.tinubu.assurance.api.dto.PolicyStatusDto;
import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class InsurancePolicyCreateCommandMapperTest {

    private final InsurancePolicyCreateCommandMapper mapper = Mappers.getMapper(InsurancePolicyCreateCommandMapper.class);


    @Test
    void Should_MapInsurancePolicyCreateCommand_When_GivenInsurancePolicyCreateReqDto() {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .name("Assurance mobile")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When
        InsurancePolicyCreateCommand command = mapper.fromInsurancePolicyCreateReqDto(dto);

        // Then
        assertThat(command).isNotNull();
        assertThat(command.getName()).isEqualTo("Assurance mobile");
        assertThat(command.getStatus().name()).isEqualTo(PolicyStatusDto.ACTIVE.name());
        assertThat(command.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(command.getEndDate()).isEqualTo(LocalDate.of(2026, 6, 1));
    }
}
