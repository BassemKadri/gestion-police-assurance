package com.tinubu.assurance.api.mapper;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateOrUpdateRespDto;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicyCreateOrUpdateRespDtoMapper {

    InsurancePolicyCreateOrUpdateRespDtoMapper INSTANCE = Mappers.getMapper(InsurancePolicyCreateOrUpdateRespDtoMapper.class);

    InsurancePolicyCreateOrUpdateRespDto fromInsurancePolicy(final InsurancePolicy insurancePolicy);
}
