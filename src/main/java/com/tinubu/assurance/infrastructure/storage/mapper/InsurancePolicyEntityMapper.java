package com.tinubu.assurance.infrastructure.storage.mapper;

import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper responsable for handling mapping for the Insurance Policy Aggregate for actual persistance based on
 * its representing of {@link InsurancePolicyEntity}
 */
@Mapper
public interface InsurancePolicyEntityMapper {

    InsurancePolicyEntityMapper INSTANCE = Mappers.getMapper(InsurancePolicyEntityMapper.class);

    /**
     * @param insurancePolicy an instance of Insurance Policy model.
     * @return {@link InsurancePolicyEntity} representing the corresponding entity representation of
     * the provided Insurance Policy
     */
    InsurancePolicyEntity fromInsurancePolicy(final InsurancePolicy insurancePolicy);
}
