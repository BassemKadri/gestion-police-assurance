package com.tinubu.assurance.domain.service;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InsurancePolicyDomainService {

    InsurancePolicy createInsurancePolicy(@NotNull final InsurancePolicyCreateCommand command);

    InsurancePolicy updateInsurancePolicy(@NotNull final InsurancePolicyUpdateCommand command);

    InsurancePolicy getInsurancePolicy(@NotNull final UUID id);

    Page<InsurancePolicy> getListInsurancePolicy(@NotNull final Pageable pageable);
}
