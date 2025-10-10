package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericResponse;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.specification.GenericSpecification;
import it.busz.spring_specification_example.specification.validation.FieldValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderService {
    private final OrderRepo orderRepo;
    private static final FieldValidator VALIDATOR = new FieldValidator(Order.class);

    OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    GenericResponse<OrderDto> searchSorting(@Valid GenericSortingRequest<Void> request) {
        VALIDATOR.validateSorting(request);

        final var pageable = request.toPageable();
        final var orders = orderRepo.findAll(pageable);
        return getResponseFor(orders);
    }

    GenericResponse<OrderDto> searchFiltering(@Valid GenericSortingRequest<OrderListRequest> request, @NotNull Long userId) {
        VALIDATOR.validateSorting(request);
        VALIDATOR.validateFilters(request.object().filters());

        final var pageable = request.toPageable();
        final var spec = getSpecificationForSearch(request.object().filters(), userId);
        final var orders = orderRepo.findAll(spec, pageable);
        return getResponseFor(orders);
    }

    private static GenericResponse<OrderDto> getResponseFor(Page<Order> orders) {
        final var orderDtos = OrderMapper.toDtoList(orders.getContent());
        return new GenericResponse<>(
                orders.getTotalElements(),
                orderDtos
        );
    }

    private Specification<Order> getSpecificationForSearch(List<GenericFilter<?>> filters, Long userId) {
        final var customSpec = Specification.allOf(OrderSpecification.forUser(userId),
                OrderSpecification.dateCreatedAfterFiveDaysToNow());
        final var filtersSpec = GenericSpecification.<Order>getFiltersSpecification(filters);

        return Specification.allOf(customSpec, filtersSpec);
    }
}
