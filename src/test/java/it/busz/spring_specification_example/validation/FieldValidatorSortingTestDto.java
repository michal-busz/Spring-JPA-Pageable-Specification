package it.busz.spring_specification_example.validation;

import it.busz.spring_specification_example.request.GenericSortingRequest;

record FieldValidatorSortingTestDto(
        GenericSortingRequest<String> sortingRequest,
        String description
) {
}