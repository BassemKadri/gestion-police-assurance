package com.tinubu.assurance.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;
import com.tinubu.assurance.api.dto.InsurancePolicyUpdateReqDto;
import com.tinubu.assurance.api.dto.PolicyStatusDto;
import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus;
import com.tinubu.assurance.infrastructure.storage.repository.InsurancePolicyEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class InsurancePolicyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InsurancePolicyEntityRepository entityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanDatabase() {
        entityRepository.deleteAll();
    }

    @Test
    void ShouldCreateInsurancePolicy_When_GivenValidRequest() throws Exception {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .name("Assurance Samsung")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Assurance Samsung"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        // Then
        var allPolicies = entityRepository.findAll();
        assertThat(allPolicies).hasSize(1);
        assertThat(allPolicies.get(0).getName()).isEqualTo(dto.getName());
    }

    @Test
    void ShouldReturn400_When_PolicyNameIsMissing() throws Exception {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When & Then
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.name").value("Le nom de la police ne doit pas être vide."));
    }

    @Test
    void ShouldReturn400_When_policyStatusIsMissing() throws Exception {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .name("Assurance Samsung")
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When & Then
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.status").value("Le statut est obligatoire."));
    }

    @Test
    void shouldReturn400_When_PolicyStatusIsInvalidEnum() throws Exception {
        // Given
        String jsonInvalidEnum = """
        {
          "name": "Assurance Samsung KO",
          "status": "ACTIVEx",
          "startDate": "2025-06-01",
          "endDate": "2026-06-01"
        }
        """;

        // When & Then
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalidEnum))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le format ou les valeurs du JSON sont incorrects. Vérifiez les types, les dates et les enums."))
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void ShouldReturn400_When_StartDateIsMissing() throws Exception {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .name("Assurance Samsung")
                .status(PolicyStatusDto.ACTIVE)
                .endDate(LocalDate.of(2026, 6, 1))
                .build();

        // When & Then
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.startDate").value("La date de début est obligatoire."));
    }

    @Test
    void ShouldReturn400_When_EndDateIsMissing() throws Exception {
        // Given
        InsurancePolicyCreateReqDto dto = InsurancePolicyCreateReqDto.builder()
                .name("Assurance Samsung")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2026, 6, 1))
                .build();

        // When & Then
        mockMvc.perform(post("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.endDate").value("La date de fin est obligatoire."));
    }

    @Test
    void ShouldUpdateInsurancePolicy_When_GivenValidRequest() throws Exception {
        // Given
        InsurancePolicyEntity initialEntity = InsurancePolicyEntity.builder()
                .name("Insurance apple")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(Instant.now().minusSeconds(3600))
                .lastUpdate(Instant.now().minusSeconds(3600))
                .build();

        InsurancePolicyEntity persisted = entityRepository.save(initialEntity);

        // Payload to update
        InsurancePolicyUpdateReqDto updateDto = InsurancePolicyUpdateReqDto.builder()
                .id(persisted.getId())
                .name("Insurance apple updated")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 7, 1))
                .endDate(LocalDate.of(2026, 7, 1))
                .build();

        // When
        mockMvc.perform(put("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$.name").value(updateDto.getName()))
                .andExpect(jsonPath("$.status").value(updateDto.getStatus().name()))
                .andExpect(jsonPath("$.startDate").value(updateDto.getStartDate().toString()))
                .andExpect(jsonPath("$.endDate").value(updateDto.getEndDate().toString()));

        // Then : vérifier la base H2
        var updated = entityRepository.findById(persisted.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo(updateDto.getName());
        assertThat(updated.get().getStatus().name()).isEqualTo(updateDto.getStatus().name());
        assertThat(updated.get().getStartDate()).isEqualTo(updateDto.getStartDate().toString());
        assertThat(updated.get().getEndDate()).isEqualTo(updateDto.getEndDate().toString());
    }


    @Test
    void Should_Return404_When_UpdatingNonExistingInsurancePolicy() throws Exception {
        // Given : un UUID qui n'existe pas en base
        UUID id = UUID.randomUUID();

        InsurancePolicyUpdateReqDto updateDto = InsurancePolicyUpdateReqDto.builder()
                .id(id)
                .name("Samsung")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(LocalDate.of(2025, 7, 1))
                .endDate(LocalDate.of(2026, 7, 1))
                .build();

        // When & Then
        mockMvc.perform(put("/api/insurance-policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("La police d’assurance avec l’ID " + id + " est introuvable."));
    }

    @Test
    void Should_ReturnInsurancePolicy_When_GivenExistingId() throws Exception {
        // Given
        InsurancePolicyEntity entity = InsurancePolicyEntity.builder()
                .name("Assurance Apple")
                .status(com.tinubu.assurance.infrastructure.storage.entity.PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(Instant.now().minusSeconds(3600))
                .lastUpdate(Instant.now().minusSeconds(1800))
                .build();

        InsurancePolicyEntity persisted = entityRepository.save(entity);

        // When & Then : requête GET avec l'ID existant
        mockMvc.perform(get("/api/insurance-policies/" + persisted.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.status").value(entity.getStatus().name()))
                .andExpect(jsonPath("$.startDate").value(entity.getStartDate().toString()))
                .andExpect(jsonPath("$.endDate").value(entity.getEndDate().toString()));
    }

    @Test
    void Should_Return404_When_InsurancePolicyNotFound() throws Exception {
        // Given : un ID qui n'existe pas
        UUID nonExistentId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(get("/api/insurance-policies/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("La police d’assurance avec l’ID " + nonExistentId + " est introuvable."));
    }

    @Test
    void Should_ReturnListInsurancePolicies_When_Requested() throws Exception {
        // Given : insérer 3 insurance policies en base
        List<InsurancePolicyEntity> policies = List.of(
                InsurancePolicyEntity.builder()
                        .name("Policy A")
                        .status(PolicyStatus.ACTIVE)
                        .startDate(LocalDate.of(2025, 6, 1))
                        .endDate(LocalDate.of(2026, 6, 1))
                        .creationDate(Instant.now().minusSeconds(3600))
                        .lastUpdate(Instant.now().minusSeconds(1800))
                        .build(),
                InsurancePolicyEntity.builder()
                        .name("Policy B")
                        .status(PolicyStatus.ACTIVE)
                        .startDate(LocalDate.of(2025, 7, 1))
                        .endDate(LocalDate.of(2026, 7, 1))
                        .creationDate(Instant.now().minusSeconds(3600))
                        .lastUpdate(Instant.now().minusSeconds(1800))
                        .build(),
                InsurancePolicyEntity.builder()
                        .name("Policy C")
                        .status(PolicyStatus.ACTIVE)
                        .startDate(LocalDate.of(2025, 8, 1))
                        .endDate(LocalDate.of(2026, 8, 1))
                        .creationDate(Instant.now().minusSeconds(3600))
                        .lastUpdate(Instant.now().minusSeconds(1800))
                        .build()
        );

        entityRepository.saveAll(policies);

        // When & Then : GET avec pagination (size = 2)
        mockMvc.perform(get("/api/insurance-policies")
                        .param("page", "0")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Policy A"));
    }


}

