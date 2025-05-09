package com.tinubu.assurance.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePolicyUpdateReqDto {

    @NotNull(message = "L identifiant est obligatoire.")
    private UUID id;

    @NotBlank(message = "Le nom de la police est obligatoire.")
    private String name;

    @NotNull(message = "Le statut est obligatoire.")
    private PolicyStatusDto status;

    @NotNull(message = "La date de début de couverture est obligatoire.")
    private LocalDate startDate;

    @NotNull(message = "La date de fin de couverture est obligatoire.")
    private LocalDate endDate;
}
