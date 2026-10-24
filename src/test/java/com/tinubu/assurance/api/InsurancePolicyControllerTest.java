package com.tinubu.assurance.api;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.tinubu.assurance.api.exception.GlobalExceptionHandler;
import com.tinubu.assurance.application.service.InsurancePolicyService;
import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import com.tinubu.assurance.domain.model.PolicyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InsurancePolicyControllerTest {

    @Mock
    private InsurancePolicyService insurancePolicyService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new InsurancePolicyController(insurancePolicyService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        Jackson2ObjectMapperBuilder.json()
                                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                .build()))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"not-a-uuid", "null"})
    void Should_Return400_When_IdIsInvalid(String id) throws Exception {
        // Given / When / Then
        mockMvc.perform(get("/api/insurance-policies/{id}", id))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(insurancePolicyService);
    }

    @Test
    void Should_Return404_When_InsurancePolicyNotFound() throws Exception {
        // Given
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        InsurancePolicyNotFoundException exception = new InsurancePolicyNotFoundException(id);
        when(insurancePolicyService.getInsurancePolicy(id)).thenThrow(exception);

        // When / Then
        mockMvc.perform(get("/api/insurance-policies/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));

        verify(insurancePolicyService).getInsurancePolicy(id);
        verifyNoMoreInteractions(insurancePolicyService);
    }

    @Test
    void Should_ReturnInsurancePolicy_When_IdExists() throws Exception {
        // Given
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000002");
        InsurancePolicy policy = InsurancePolicy.builder()
                .id(id)
                .name("Assurance Auto")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2026, 6, 1))
                .creationDate(Instant.parse("2025-01-01T10:00:00Z"))
                .lastUpdate(Instant.parse("2025-02-01T10:00:00Z"))
                .build();
        when(insurancePolicyService.getInsurancePolicy(id)).thenReturn(policy);

        // When / Then
        mockMvc.perform(get("/api/insurance-policies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value(policy.getName()))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.startDate").value("2025-06-01"))
                .andExpect(jsonPath("$.endDate").value("2026-06-01"))
                .andExpect(jsonPath("$.creationDate").value("2025-01-01T10:00:00Z"))
                .andExpect(jsonPath("$.lastUpdate").value("2025-02-01T10:00:00Z"));

        verify(insurancePolicyService).getInsurancePolicy(id);
        verifyNoMoreInteractions(insurancePolicyService);
    }
}
