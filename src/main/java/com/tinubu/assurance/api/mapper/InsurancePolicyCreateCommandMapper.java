package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicyCreateCommandMapper {

    InsurancePolicyCreateCommandMapper INSTANCE = Mappers.getMapper(InsurancePolicyCreateCommandMapper.class);

    /**
     * @param insurancePolicyCreateReqDto the request DTO to create a new Insurance Policy
     * @return {@link InsurancePolicyCreateCommand} representing an internal domain command to create a new Insurance Policy
     */
    InsurancePolicyCreateCommand fromInsurancePolicyCreateReqDto(final InsurancePolicyCreateReqDto insurancePolicyCreateReqDto);

}
