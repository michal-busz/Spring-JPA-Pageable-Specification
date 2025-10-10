package it.busz.spring_specification_example.validation;

import it.busz.spring_specification_example.request.GenericFilter;

import java.util.List;

record FieldValidatorFilteringTestDto(
        List<GenericFilter<?>> filters,
        String description
) {
}