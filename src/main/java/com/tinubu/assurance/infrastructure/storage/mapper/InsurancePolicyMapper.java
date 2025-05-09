package com.tinubu.assurance.infrastructure.storage.mapper;

import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicyMapper {

    InsurancePolicyMapper INSTANCE = Mappers.getMapper(InsurancePolicyMapper.class);

    /**
     * @param insurancePolicyEntity the entity representation of the provided Insurance Policy
     * @return {@link InsurancePolicy} representing the model corresponding with the provided entity
     * representation
     */
    InsurancePolicy fromInsurancePolicyEntity(final InsurancePolicyEntity insurancePolicyEntity);
}
