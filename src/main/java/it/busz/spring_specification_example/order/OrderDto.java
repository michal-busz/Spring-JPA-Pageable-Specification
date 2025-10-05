package it.busz.spring_specification_example.order;

import java.time.ZonedDateTime;

record OrderDto(
        Long id,
        Long userIdentifier, // If you name it userId as in entity, you will allow for access to other users' data via =,<,> operators. The same happens for String with IN and OR operators.
        String description,
        ZonedDateTime dateCreated,
        OrderStatus status
) {
}
