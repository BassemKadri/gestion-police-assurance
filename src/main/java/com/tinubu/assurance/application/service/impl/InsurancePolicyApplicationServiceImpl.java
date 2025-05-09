package com.tinubu.assurance.application.service.impl;

import com.tinubu.assurance.application.service.InsurancePolicyService;
import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.service.InsurancePolicyDomainService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class InsurancePolicyApplicationServiceImpl implements InsurancePolicyService {

    final InsurancePolicyDomainService insurancePolicyService;

    @Override
    public InsurancePolicy createInsurancePolicy(final InsurancePolicyCreateCommand command) {
        return insurancePolicyService.createInsurancePolicy(command);
    }

    @Override
    public InsurancePolicy updateInsurancePolicy(final InsurancePolicyUpdateCommand command) {
        return insurancePolicyService.updateInsurancePolicy(command);
    }

    @Override
    public InsurancePolicy getInsurancePolicy(final UUID id) {
        return insurancePolicyService.getInsurancePolicy(id);
    }

    @Override
    public Page<InsurancePolicy> listInsurancePolicies(final Pageable pageable) {
        return insurancePolicyService.getListInsurancePolicy(pageable);
    }

}
