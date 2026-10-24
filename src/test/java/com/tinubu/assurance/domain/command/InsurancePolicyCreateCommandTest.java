package com.tinubu.assurance.domain.command;

import com.tinubu.assurance.domain.model.PolicyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsurancePolicyCreateCommandTest {

    private static final LocalDate START_DATE = LocalDate.of(2026, 10, 24);
    private static final LocalDate END_DATE = START_DATE.plusYears(1);

    @Test
    void Should_RejectCommand_When_NameIsNull() {
        // Given / When / Then
        assertThatThrownBy(() -> new InsurancePolicyCreateCommand(null, PolicyStatus.ACTIVE, START_DATE, END_DATE))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void Should_RejectCommand_When_NameIsBlank(String name) {
        // Given / When / Then
        assertThatThrownBy(() -> new InsurancePolicyCreateCommand(name, PolicyStatus.ACTIVE, START_DATE, END_DATE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Should_RejectCommand_When_StartDateIsNull() {
        // Given / When / Then
        assertThatThrownBy(() -> new InsurancePolicyCreateCommand("Assurance mobile", PolicyStatus.ACTIVE, null, END_DATE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("startDate ne doit pas etre null");
    }

    @Test
    void Should_RejectCommand_When_EndDateIsNull() {
        // Given / When / Then
        assertThatThrownBy(() -> new InsurancePolicyCreateCommand("Assurance mobile", PolicyStatus.ACTIVE, START_DATE, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("endDate ne doit pas etre null");
    }

    @ParameterizedTest
    @EnumSource(PolicyStatus.class)
    void Should_PreserveValues_When_CommandIsValid(PolicyStatus status) {
        // Given
        String name = "Assurance mobile";

        // When
        InsurancePolicyCreateCommand command = new InsurancePolicyCreateCommand(name, status, START_DATE, END_DATE);

        // Then
        assertThat(command.getName()).isEqualTo(name);
        assertThat(command.getStatus()).isEqualTo(status);
        assertThat(command.getStartDate()).isEqualTo(START_DATE);
        assertThat(command.getEndDate()).isEqualTo(END_DATE);
    }
}
