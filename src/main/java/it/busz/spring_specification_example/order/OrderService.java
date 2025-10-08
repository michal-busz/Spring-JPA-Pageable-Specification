package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericResponse;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.specification.GenericSpecification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
class OrderService {
    private static final List<String> ALLOWED_FILTER_FIELDS = Arrays.stream(OrderDto.class.getDeclaredFields())
            .map(Field::getName)
            .toList();
    private final OrderRepo orderRepo;

    OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    private static GenericResponse<OrderDto> getResponseFor(Page<Order> orders) {
        final var orderDtos = OrderMapper.toDtoList(orders.getContent());
        return new GenericResponse<>(
                orders.getTotalElements(),
                orderDtos
        );
    }

    GenericResponse<OrderDto> searchSorting(@Valid GenericSortingRequest<Void> request) {
        final var pageable = request.toPageable(ALLOWED_FILTER_FIELDS);
        final var orders = orderRepo.findAll(pageable);
        return getResponseFor(orders);
    }

    GenericResponse<OrderDto> searchFiltering(@Valid GenericSortingRequest<OrderListRequest> request, @NotNull Long userId) {
        final var pageable = request.toPageable(ALLOWED_FILTER_FIELDS);
        final var spec = getSpecificationForSearch(request.object().filters(), userId);
        final var orders = orderRepo.findAll(spec, pageable);
        return getResponseFor(orders);
    }

    private Specification<Order> getSpecificationForSearch(List<GenericFilter> filters, Long userId) {
        final var customSpec = Specification.allOf(OrderSpecification.forUser(userId),
                OrderSpecification.dateCreatedAfterFiveDaysToNow());
        final var filtersSpec = GenericSpecification.<Order>getFiltersSpecification(filters, ALLOWED_FILTER_FIELDS);

        return Specification.allOf(customSpec, filtersSpec);
    }
}
