package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;
import com.tinubu.assurance.api.dto.InsurancePolicyUpdateReqDto;
import com.tinubu.assurance.api.dto.PolicyStatusDto;
import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsurancePolicyUpdateCommandMapperTest {

    InsurancePolicyUpdateCommandMapper mapper = Mappers.getMapper(InsurancePolicyUpdateCommandMapper.class);

    @Test
    void Should_MapInsurancePolicyUpdateCommand_When_GivenInsurancePolicyUpdateReqDto() {
        // Given
        UUID id = UUID.randomUUID();
        InsurancePolicyUpdateReqDto dto = InsurancePolicyUpdateReqDto.builder()
                .id(id)
                .name("Assurance mobile")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When
        InsurancePolicyUpdateCommand command = mapper.fromInsurancePolicyUpdateReqDto(dto);

        // Then
        assertThat(command).isNotNull();
        assertThat(command.getId()).isEqualTo(id);
        assertThat(command.getName()).isEqualTo("Assurance mobile");
        assertThat(command.getStatus().name()).isEqualTo(PolicyStatusDto.ACTIVE.name());
        assertThat(command.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(command.getEndDate()).isEqualTo(LocalDate.of(2026, 6, 1));
    }
}
