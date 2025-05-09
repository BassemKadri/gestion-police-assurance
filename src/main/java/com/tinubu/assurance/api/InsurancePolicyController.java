package com.tinubu.assurance.api;

import com.tinubu.assurance.api.dto.InsurancePolicyCreateOrUpdateRespDto;
import com.tinubu.assurance.domain.exception.InsurancePolicyNotFoundException;
import com.tinubu.assurance.api.dto.InsurancePolicyCreateReqDto;
import com.tinubu.assurance.api.dto.InsurancePolicyUpdateReqDto;
import com.tinubu.assurance.api.mapper.InsurancePolicyCreateCommandMapper;
import com.tinubu.assurance.api.mapper.InsurancePolicyCreateOrUpdateRespDtoMapper;
import com.tinubu.assurance.api.mapper.InsurancePolicyUpdateCommandMapper;
import com.tinubu.assurance.application.service.InsurancePolicyService;
import com.tinubu.assurance.domain.command.InsurancePolicyCreateCommand;
import com.tinubu.assurance.domain.command.InsurancePolicyUpdateCommand;
import com.tinubu.assurance.domain.model.InsurancePolicy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/insurance-policies")
@AllArgsConstructor
public class InsurancePolicyController {

    final InsurancePolicyService insurancePolicyService;

    /**
     * Create New Insurance Policy
     *
     * @param insurancePolicyCreateReqDto the request body dto containing the data for the new insurance policy
     * @return a response DTO that containing the newly created Insurance Policy
     */
    @Operation(summary = "Create a new insurance policy", description = "Creates a new insurance policy based on the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insurance policy created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<InsurancePolicyCreateOrUpdateRespDto> createInsurancePolicies(@RequestBody @Valid final InsurancePolicyCreateReqDto insurancePolicyCreateReqDto) {
        InsurancePolicyCreateCommand command = InsurancePolicyCreateCommandMapper.INSTANCE.fromInsurancePolicyCreateReqDto(insurancePolicyCreateReqDto);
        InsurancePolicy insurancePolicy = insurancePolicyService.createInsurancePolicy(command);
        InsurancePolicyCreateOrUpdateRespDto response =
                InsurancePolicyCreateOrUpdateRespDtoMapper.INSTANCE.fromInsurancePolicy(insurancePolicy);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update an existing Insurance Policy
     *
     * @param insurancePolicyUpdateReqDto the request body dto containing the updated data for the insurance policy,
     *                                    including the policy's unique ID and new values
     * @return a response DTO that containing the updated Insurance Policy
     * @throws InsurancePolicyNotFoundException if the policy with the given ID does not exist
     */
    @Operation(summary = "Update an existing insurance policy", description = "Updates an existing insurance policy by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insurance policy updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Insurance Policy not found")
    })
    @PutMapping
    public ResponseEntity<InsurancePolicyCreateOrUpdateRespDto> updateInsurancePolicy(@RequestBody @Valid final InsurancePolicyUpdateReqDto insurancePolicyUpdateReqDto) {
        InsurancePolicyUpdateCommand command = InsurancePolicyUpdateCommandMapper.INSTANCE.fromInsurancePolicyUpdateReqDto(insurancePolicyUpdateReqDto);
        InsurancePolicy updatedPolicy = insurancePolicyService.updateInsurancePolicy(command);
        InsurancePolicyCreateOrUpdateRespDto response =
                InsurancePolicyCreateOrUpdateRespDtoMapper.INSTANCE.fromInsurancePolicy(updatedPolicy);

        return ResponseEntity
                .ok(response);
    }

    /**
     * Retrieves an insurance policy by its unique identifier.
     *
     * @param id the UUID of the insurance policy
     * @return response DTO containing the insurance policy details
     * @throws InsurancePolicyNotFoundException if the policy with the given ID does not exist
     */
    @Operation(summary = "Get an insurance policy by ID", description = "Retrieves insurance policy details by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insurance Policy found"),
            @ApiResponse(responseCode = "404", description = "Insurance Policy not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicyCreateOrUpdateRespDto> getInsurancePolicyById(@PathVariable @NotNull final UUID id) {
        InsurancePolicy policy = insurancePolicyService.getInsurancePolicy(id);
        InsurancePolicyCreateOrUpdateRespDto response = InsurancePolicyCreateOrUpdateRespDtoMapper.INSTANCE.fromInsurancePolicy(policy);

        return ResponseEntity
                .ok(response);
    }

    /**
     * Retrieves a paginated list of all insurance policies.
     *
     * @param pageable the pagination information (page, size, sort)
     * @return a list of insurance policy response DTOs
     * Exp call : localhost:8080/api/insurance-policies?page=2&size=3&sort=name,asc
     */
    @Operation(summary = "List all insurance policies", description = "Returns a paginated list of insurance policies.")
    @ApiResponse(responseCode = "200", description = "List of insurance policies")
    @GetMapping
    public ResponseEntity<List<InsurancePolicyCreateOrUpdateRespDto>> listInsurancePolicies(@NotNull final Pageable pageable) {
        Page<InsurancePolicy> policiesPage = insurancePolicyService.listInsurancePolicies(pageable);

        List<InsurancePolicyCreateOrUpdateRespDto> responseList =
                policiesPage
                        .map(InsurancePolicyCreateOrUpdateRespDtoMapper.INSTANCE::fromInsurancePolicy)
                        .getContent();

        return ResponseEntity
                .ok(responseList);
    }

}
