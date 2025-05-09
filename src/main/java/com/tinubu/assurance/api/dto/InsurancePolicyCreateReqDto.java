package com.tinubu.assurance.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for creating an insurance policy")
public class InsurancePolicyCreateReqDto {

        @NotBlank(message = "Le nom de la police ne doit pas être vide.")
        String name;

        @NotNull(message = "Le statut est obligatoire.")
        @Schema(description = "Policy status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE"})
        PolicyStatusDto status;

        @NotNull(message = "La date de début est obligatoire.")
        @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur.")
        LocalDate startDate;

        @NotNull(message = "La date de fin est obligatoire.")
        @Future(message = "La date de fin doit être dans le futur.")
        LocalDate endDate;
}


