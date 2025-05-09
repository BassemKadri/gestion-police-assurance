package com.tinubu.assurance.domain.service.impl;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.mapper.InsurancePolicyMapper;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.repository.InsurancePolicyRepository;
import com.tinubu.assurance.domain.service.InsurancePolicyDomainService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InsurancePolicyDomainServiceImpl implements InsurancePolicyDomainService {

    final InsurancePolicyRepository insurancePolicyRepository;
    @Override
    public InsurancePolicy createInsurancePolicy(final InsurancePolicyCreateCommand command) {
        InsurancePolicy insurancePolicy = InsurancePolicyMapper.INSTANCE.fromInsurancePolicyCreateCommand(command);
        return insurancePolicyRepository.saveOrUpdate(insurancePolicy);
    }

    @Override
    public InsurancePolicy updateInsurancePolicy(final InsurancePolicyUpdateCommand command) {
        InsurancePolicy oldInsurancePolicy = this.getInsurancePolicy(command.getId());
        InsurancePolicy newInsurancePolicy = InsurancePolicyMapper.INSTANCE.fromInsurancePolicyUpdateCommand(command);
        InsurancePolicy updatedInsurancePolicy = InsurancePolicyMapper.INSTANCE.update(oldInsurancePolicy, newInsurancePolicy);
        return insurancePolicyRepository.saveOrUpdate(updatedInsurancePolicy);
    }

    @Override
    public InsurancePolicy getInsurancePolicy(final UUID id) {
        return insurancePolicyRepository.get(id);
    }

    @Override
    public Page<InsurancePolicy> getListInsurancePolicy(final Pageable pageable) {
        return insurancePolicyRepository.getListInsurancePolicies(pageable);
    }
}
