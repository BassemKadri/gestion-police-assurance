package com.tinubu.assurance.domain.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Exception notifying the situation were insurance policy not exist
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class InsurancePolicyNotFoundException extends RuntimeException {

    @NotNull
    final UUID id;

    public InsurancePolicyNotFoundException(UUID id) {
        super("La police d’assurance avec l’ID " + id + " est introuvable.");
        this.id = id;
    }
}
