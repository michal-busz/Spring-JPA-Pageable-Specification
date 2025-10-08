package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.specification.GenericSpecification;
import it.busz.spring_specification_example.request.FilterOperator;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

class OrderSpecification {
    private OrderSpecification() {

    }

    static Specification<Order> forUser(Long userId) {
        return GenericSpecification.specificationFor("userId", FilterOperator.EQUALS, userId);
    }

    static Specification<Order> dateCreatedAfterFiveDaysToNow() {
        return GenericSpecification.specificationFor("dateCreated", FilterOperator.GREATER_THAN_EQUALS, ZonedDateTime.now().minusDays(5));
    }
}
