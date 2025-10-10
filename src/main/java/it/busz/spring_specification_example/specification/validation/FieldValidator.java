package it.busz.spring_specification_example.specification.validation;

import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.specification.SpecificationError;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record FieldValidator(Set<String> allowedFields) {

    public FieldValidator(Class<?> entityClass) {
        this(Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(FilterExclude.class))
                .map(Field::getName)
                .collect(Collectors.toSet()));
    }


    public void validateFilters(List<GenericFilter<?>> filters) {
        filters.forEach(filter -> {
            if (!allowedFields.contains(filter.getField())) {
                throw new IllegalArgumentException(SpecificationError.INVALID_FILTER_FIELD.getErrorCode());
            }
        });
    }

    public void validateSorting(GenericSortingRequest<?> request) {
        if (!allowedFields.contains(request.sortBy())) {
            throw new IllegalArgumentException(SpecificationError.INVALID_FILTER_FIELD.getErrorCode());
        }
    }
}