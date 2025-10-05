package it.busz.spring_specification_example.order;

import java.util.List;

class OrderMapper {
    private OrderMapper() {
    }

    static List<OrderDto> toDtoList(List<Order> order){
        return order.stream()
                .map(OrderMapper::toDto)
                .toList();
    }
    static OrderDto toDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getDescription(),
                order.getDateCreated(),
                order.getStatus()
        );
    }

}
