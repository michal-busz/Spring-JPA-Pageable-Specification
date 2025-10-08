package it.busz.spring_specification_example.order;

import java.time.ZonedDateTime;

record OrderDto(
        Long id,
        Long userId,
        String description,
        ZonedDateTime dateCreated,
        String status
) {
}
