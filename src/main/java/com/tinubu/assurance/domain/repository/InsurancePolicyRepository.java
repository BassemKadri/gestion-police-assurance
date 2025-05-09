package com.tinubu.assurance.domain.repository;

import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository handling persistance and retrieval operation over the Insurance Policy Aggregate
 */
public interface InsurancePolicyRepository {

    /**
     * @param insurancePolicy represents the Insurance Policy to be persisted or updated
     * @return {@link InsurancePolicy} representing the resulted Insurance Policy after persistance or update
     */
    InsurancePolicy saveOrUpdate(@NotNull final InsurancePolicy insurancePolicy);

    /**
     * @param id the identifier of the insurance policy
     * @return an insurance policy {@link InsurancePolicy}
     * @throws InsurancePolicyNotFoundException if the insurance policy to update not found
     */
    InsurancePolicy get(@NotNull final UUID id);

    /**
     * @param pageable the pagination information (page, size, sort)
     * @return a paged list of insurance policy {@link InsurancePolicy}
     */
    Page<InsurancePolicy> getListInsurancePolicies(@NotNull final Pageable pageable);
}
