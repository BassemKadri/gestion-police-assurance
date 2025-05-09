package com.tinubu.assurance.domain.command;

import com.tinubu.assurance.domain.model.PolicyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * The command to create an Insurance Policy
 */
@Value
public class InsurancePolicyCreateCommand {

    @NotBlank String name;
    @NotBlank PolicyStatus status;
    @NotBlank LocalDate startDate;
    @NotBlank LocalDate endDate;

    @Builder
    public InsurancePolicyCreateCommand(
            @NotBlank final String name,
            @NotBlank final PolicyStatus status,
            @NotNull final LocalDate startDate,
            @NotNull final LocalDate endDate
    ) {
        this.name = notBlank(name);
        this.status = status;
        this.startDate = notNull(startDate, "startDate ne doit pas etre null");
        this.endDate = notNull(endDate, "endDate ne doit pas etre null");
    }
}
