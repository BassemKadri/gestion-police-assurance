package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;
import com.tinubu.assurance.api.dto.InsurancePolicyUpdateReqDto;
import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface InsurancePolicyUpdateCommandMapper {

    InsurancePolicyUpdateCommandMapper INSTANCE = Mappers.getMapper(InsurancePolicyUpdateCommandMapper.class);

    /**
     * @param insurancePolicyUpdateReqDto the request DTO to update an Insurance Policy
     * @return {@link InsurancePolicyUpdateCommand} representing an internal domain command to update an Insurance Policy
     */
    InsurancePolicyUpdateCommand fromInsurancePolicyUpdateReqDto(final InsurancePolicyUpdateReqDto insurancePolicyUpdateReqDto);

}
