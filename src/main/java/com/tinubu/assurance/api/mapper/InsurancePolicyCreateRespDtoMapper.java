package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateRespDto;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicyCreateRespDtoMapper {

    InsurancePolicyCreateRespDtoMapper INSTANCE = Mappers.getMapper(InsurancePolicyCreateRespDtoMapper.class);

    InsurancePolicyCreateRespDto fromInsurancePolicy(final InsurancePolicy insurancePolicy);
}
