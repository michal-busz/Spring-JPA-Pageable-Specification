package it.busz.spring_specification_example.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GenericFilter(
        @NotEmpty
        String field,
        @NotNull
        FilterOperator operator,
        @NotNull
        Object value
) {
}
