package it.busz.spring_specification_example.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record GenericSortingRequest<T>(
        @Min(0)
        @NotNull
        int pageNumber,
        @Min(1)
        @NotNull
        int pageSize,
        @NotEmpty
        String sortBy,
        @NotNull
        SortOrder sortOrder,
        T object
) {
    public Pageable toPageable() {
        final var direction = sortOrder.toDirection();
        final var sort = Sort.by(direction, sortBy);
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
