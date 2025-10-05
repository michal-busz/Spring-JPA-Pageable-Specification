package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericResponse;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import org.springframework.stereotype.Service;

@Service
class OrderService {

    private final OrderRepo orderRepo;

    OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    GenericResponse<OrderDto> findSorting(GenericSortingRequest<Void> request){
        final var pageable = request.toPageable();
        final var orders = orderRepo.findAll(pageable);
        final var orderDtos = OrderMapper.toDtoList(orders.getContent());
        return new GenericResponse<>(
                orders.getTotalElements(),
                orderDtos
        );
    }
}
