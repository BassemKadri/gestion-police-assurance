package com.tinubu.assurance.infrastructure.storage.repository;

import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.repository.InsurancePolicyRepository;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import com.tinubu.assurance.infrastructure.storage.mapper.InsurancePolicyEntityMapper;
import com.tinubu.assurance.infrastructure.storage.mapper.InsurancePolicyMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Repository handling persistence and retrieval operation over the Insurance Policy Aggregate
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class InsurancePolicyRepositoryImpl implements InsurancePolicyRepository {

    final InsurancePolicyEntityRepository insurancePolicyEntityRepository;

    @Override
    public InsurancePolicy saveOrUpdate(final InsurancePolicy insurancePolicy) {
        InsurancePolicyEntity insurancePolicyEntity = InsurancePolicyEntityMapper.INSTANCE.fromInsurancePolicy(insurancePolicy);
        final InsurancePolicyEntity entity = insurancePolicyEntityRepository.save(insurancePolicyEntity);
        return InsurancePolicyMapper.INSTANCE.fromInsurancePolicyEntity(entity);
    }

    @Override
    public InsurancePolicy get(final UUID id) {
        return insurancePolicyEntityRepository.findById(id)
                .map(InsurancePolicyMapper.INSTANCE::fromInsurancePolicyEntity)
                .orElseThrow(() -> new InsurancePolicyNotFoundException(id));
    }

    @Override
    public Page<InsurancePolicy> getListInsurancePolicies(final Pageable pageable) {
        return insurancePolicyEntityRepository.findAll(pageable)
                .map(InsurancePolicyMapper.INSTANCE::fromInsurancePolicyEntity);
    }

}
