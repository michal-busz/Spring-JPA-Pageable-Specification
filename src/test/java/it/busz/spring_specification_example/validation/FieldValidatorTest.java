package it.busz.spring_specification_example.validation;

import it.busz.spring_specification_example.specification.SpecificationError;
import it.busz.spring_specification_example.specification.validation.FieldValidator;
import it.busz.spring_specification_example.specification.validation.FilterExclude;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidatorTest {

    private static final FieldValidator VALIDATOR = new FieldValidator(TestEntity.class);

    private static class TestEntity {
        private Long id;
        private String name;
        private String description;
        private ZonedDateTime createdAt;
        private String status;

        @FilterExclude
        private String internalNote;

        @FilterExclude
        private String sensitiveData;

        private String publicField;
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.validation.FieldValidatorTestDataProvider#validFiltering")
    void validFiltering(@NotNull FieldValidatorFilteringTestDto testDto) {
        // when & then
        assertDoesNotThrow(() -> VALIDATOR.validateFilters(testDto.filters()));
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.validation.FieldValidatorTestDataProvider#invalidFiltering")
    void invalidFiltering(@NotNull FieldValidatorFilteringTestDto testDto) {
        // when & then
        final var exception = assertThrows(
                IllegalArgumentException.class,
                () -> VALIDATOR.validateFilters(testDto.filters())
        );
        assertEquals(SpecificationError.INVALID_FILTER_FIELD.getErrorCode(), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.validation.FieldValidatorTestDataProvider#validSorting")
    void validSorting(@NotNull FieldValidatorSortingTestDto testDto) {
        // when & then
        assertDoesNotThrow(() -> VALIDATOR.validateSorting(testDto.sortingRequest()));
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.validation.FieldValidatorTestDataProvider#invalidSorting")
    void invalidSorting(@NotNull FieldValidatorSortingTestDto testDto) {
        // when & then
        final var exception = assertThrows(
                IllegalArgumentException.class,
                () -> VALIDATOR.validateSorting(testDto.sortingRequest())
        );
        assertEquals(SpecificationError.INVALID_FILTER_FIELD.getErrorCode(), exception.getMessage());
    }

    @Test
    void shouldReturnCorrectAllowedFields() {
        // when
        Set<String> allowedFields = VALIDATOR.allowedFields();

        // then
        assertNotNull(allowedFields);

        assertTrue(allowedFields.contains("id"));
        assertTrue(allowedFields.contains("name"));
        assertTrue(allowedFields.contains("description"));
        assertTrue(allowedFields.contains("createdAt"));
        assertTrue(allowedFields.contains("status"));
        assertTrue(allowedFields.contains("publicField"));

        assertFalse(allowedFields.contains("internalNote"));
        assertFalse(allowedFields.contains("sensitiveData"));

        assertEquals(6, allowedFields.size());
    }
}