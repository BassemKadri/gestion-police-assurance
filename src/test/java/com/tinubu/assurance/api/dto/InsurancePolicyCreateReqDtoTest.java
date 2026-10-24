package com.tinubu.assurance.api.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InsurancePolicyCreateReqDtoTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-10-24T12:00:00Z"), ZoneOffset.UTC);
    private static final LocalDate TODAY = LocalDate.now(CLOCK);
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.byDefaultProvider().configure()
                .clockProvider(() -> CLOCK).buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void Should_RejectRequest_When_FieldIsInvalid(String field, Object value,
                                                 Class<? extends Annotation> constraint, String message) {
        // Given
        InsurancePolicyCreateReqDto request = validRequest();
        switch (field) {
            case "name" -> request.setName((String) value);
            case "status" -> request.setStatus((PolicyStatusDto) value);
            case "startDate" -> request.setStartDate((LocalDate) value);
            case "endDate" -> request.setEndDate((LocalDate) value);
            default -> throw new IllegalArgumentException(field);
        }

        // When
        var violations = validator.validate(request);

        // Then
        assertThat(violations).singleElement().satisfies(violation -> {
            assertThat(violation.getPropertyPath().toString()).isEqualTo(field);
            assertThat(violation.getConstraintDescriptor().getAnnotation().annotationType()).isEqualTo(constraint);
            assertThat(violation.getMessage()).isEqualTo(message);
        });
    }

    static Stream<Arguments> invalidRequests() {
        return Stream.of(
                arguments("name", null, NotBlank.class, "Le nom de la police ne doit pas être vide."),
                arguments("name", "", NotBlank.class, "Le nom de la police ne doit pas être vide."),
                arguments("name", "   ", NotBlank.class, "Le nom de la police ne doit pas être vide."),
                arguments("status", null, NotNull.class, "Le statut est obligatoire."),
                arguments("startDate", null, NotNull.class, "La date de début est obligatoire."),
                arguments("startDate", TODAY.minusDays(1), FutureOrPresent.class,
                        "La date de début doit être aujourd'hui ou dans le futur."),
                arguments("endDate", null, NotNull.class, "La date de fin est obligatoire."),
                arguments("endDate", TODAY.minusDays(1), Future.class, "La date de fin doit être dans le futur."),
                arguments("endDate", TODAY, Future.class, "La date de fin doit être dans le futur.")
        );
    }

    @ParameterizedTest
    @EnumSource(PolicyStatusDto.class)
    void Should_AcceptRequest_When_StatusIsValid(PolicyStatusDto status) {
        // Given
        InsurancePolicyCreateReqDto request = validRequest();
        request.setStatus(status);

        // When
        var violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("validStartDates")
    void Should_AcceptRequest_When_StartDateIsTodayOrFuture(LocalDate startDate) {
        // Given
        InsurancePolicyCreateReqDto request = validRequest();
        request.setStartDate(startDate);

        // When
        var violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    static Stream<LocalDate> validStartDates() {
        return Stream.of(TODAY, TODAY.plusDays(1));
    }

    private static InsurancePolicyCreateReqDto validRequest() {
        return InsurancePolicyCreateReqDto.builder()
                .name("Assurance mobile")
                .status(PolicyStatusDto.ACTIVE)
                .startDate(TODAY)
                .endDate(TODAY.plusDays(1))
                .build();
    }
}
