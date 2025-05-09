package com.tinubu.assurance.application.service;

import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Application Service responsable of triggering actions or handling queries involving the Insurance Policy Aggregate
 */
public interface InsurancePolicyService {

    /**
     * @param command Internal command to create a new Insurance Policy
     * @return an Insurance policy of {@link InsurancePolicy} representing the newly create Insurance Policy.
     */
    InsurancePolicy createInsurancePolicy(@NotNull final InsurancePolicyCreateCommand command);

    /**
     * @param command an Insurance policy of {@link InsurancePolicy} representing the newly update Insurance Policy.
     * @return an Insurance policy of {@link InsurancePolicy} representing the updated Insurance Policy.
     * @throws InsurancePolicyNotFoundException if the insurance policy to update not found
     */
    InsurancePolicy updateInsurancePolicy(@NotNull final InsurancePolicyUpdateCommand command);

    /**
     * @param id id the UUID of the insurance policy
     * @return an Insurance policy of {@link InsurancePolicy} representing the exisiting Insurance Policy.
     * @throws InsurancePolicyNotFoundException if the insurance policy to update not found
     */
    InsurancePolicy getInsurancePolicy(@NotNull final UUID id);

    /**
     * @param pageable pageable the pagination information (page, size, sort)
     * @return a paged list of insurance policy response Insurance Policy {@link InsurancePolicy}
     */
    Page<InsurancePolicy> listInsurancePolicies(@NotNull final Pageable pageable);
}
