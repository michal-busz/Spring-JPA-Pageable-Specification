package it.busz.spring_specification_example.specification;

import it.busz.spring_specification_example.request.FilterOperator;
import it.busz.spring_specification_example.request.GenericFilter;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;

public class GenericSpecification {

    public static <T, V> Specification<T> specificationFor(@NotNull String field, @NotNull FilterOperator operator, @NotNull V value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final var expression = root.get(field);
            final var mappedValue = valueToType(value, root, field);
            return switch (operator) {
                case EQUALS -> criteriaBuilder.equal(expression, value);
                case LESS_THAN -> predicateFor(expression, mappedValue, criteriaBuilder::lessThan);
                case LESS_THAN_EQUALS -> predicateFor(expression, mappedValue, criteriaBuilder::lessThanOrEqualTo);
                case GREATER_THAN -> predicateFor(expression, mappedValue, criteriaBuilder::greaterThan);
                case GREATER_THAN_EQUALS ->
                        predicateFor(expression, mappedValue, criteriaBuilder::greaterThanOrEqualTo);
            };
        };

    }

    public static <T> Specification<T> getFiltersSpecification(@NotEmpty List<GenericFilter> filterList, @NotNull List<String> allowedFields) throws IllegalArgumentException{
        var resultSpec = Specification.<T>allOf();

        for (final var filter : filterList) {
            if(!allowedFields.contains(filter.field())){
                throw new IllegalArgumentException(SpecificationError.INVALID_FILTER_FIELD.getErrorCode());
            }
            final var filterSpec = GenericSpecification.<T, Object>specificationFor(filter.field(), filter.operator(), filter.value());
            resultSpec = resultSpec.and(filterSpec);
        }
        return resultSpec;
    }

    private static <V> Predicate predicateFor(Path<?> path, V value, BasicComparison function) {
        return switch (value) {
            case Long l -> function.apply(path.as(Long.class), l);
            case String s -> function.apply(path.as(String.class), s);
            case ZonedDateTime t -> function.apply(path.as(ZonedDateTime.class), t);
            default -> throw new IllegalArgumentException(SpecificationError.INVALID_FILTER_VALUE.getErrorCode());
        };
    }

    private static <T, V> Object valueToType(V value, Root<T> root, String fieldName) {
        final var type = classForField(root, fieldName);
        if (type.isAssignableFrom(value.getClass())) {
            return value;
        } else {
            throw new IllegalArgumentException(SpecificationError.INVALID_FILTER_VALUE.getErrorCode());
        }
    }

    private static <T> Class<?> classForField(Root<T> root, String fieldName) {
        final var fieldClass = root.getModel().getAttribute(fieldName);
        return fieldClass.getJavaType();
    }

    @FunctionalInterface
    private interface BasicComparison {
        <V extends Comparable<? super V>> Predicate apply(Expression<? extends V> expression, V value);
    }
}
