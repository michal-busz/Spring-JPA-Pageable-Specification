package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericFilter;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

record OrderListRequest(
        @NotEmpty
        List<GenericFilter> filters
) {
}
