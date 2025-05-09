package com.tinubu.assurance.domain.mapper;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

@Mapper
public interface InsurancePolicyMapper {

    InsurancePolicyMapper INSTANCE = Mappers.getMapper(InsurancePolicyMapper.class);

    InsurancePolicy fromInsurancePolicyCreateCommand(final InsurancePolicyCreateCommand command);

    InsurancePolicy fromInsurancePolicyUpdateCommand(final InsurancePolicyUpdateCommand command);

    default InsurancePolicy update(InsurancePolicy oldInsurancePolicy, InsurancePolicy newInsurancePolicy) {
        InsurancePolicy.InsurancePolicyBuilder builder =
                update(oldInsurancePolicy.toBuilder(), newInsurancePolicy);
        builder.creationDate(oldInsurancePolicy.getCreationDate());
        builder.lastUpdate(Instant.now());

        return builder.build();
    }

    InsurancePolicy.InsurancePolicyBuilder update(@MappingTarget InsurancePolicy.InsurancePolicyBuilder result, InsurancePolicy insurancePolicy);

}
