package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.FilterOperator;
import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.request.SortOrder;
import org.junit.jupiter.params.provider.Arguments;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static it.busz.spring_specification_example.order.OrderTestConstants.*;

final class OrderTestDataProvider {

    private OrderTestDataProvider() {
    }

    static Stream<Arguments> sortingWithPagination() {
        return Stream.of(
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, ID_FIELD, SortOrder.ASC, null), "Full page - Sort by ID ascending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, ID_FIELD, SortOrder.DESC, null), "Full page - Sort by ID descending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, DESCRIPTION_FIELD, SortOrder.ASC, null), "Full page - Sort by Description ascending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, DESCRIPTION_FIELD, SortOrder.DESC, null), "Full page - Sort by Description descending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, STATUS_FIELD, SortOrder.ASC, null), "Full page - Sort by Status ascending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, STATUS_FIELD, SortOrder.DESC, null), "Full page - Sort by Status descending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, DATE_CREATED_FIELD, SortOrder.ASC, null), "Full page - Sort by DateCreated ascending", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, DATE_CREATED_FIELD, SortOrder.DESC, null), "Full page - Sort by DateCreated descending", false)),

                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 3, ID_FIELD, SortOrder.ASC, null), "First page (3 items) sorted by ID ASC", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 3, DATE_CREATED_FIELD, SortOrder.DESC, null), "First page (3 items) sorted by DateCreated DESC", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(1, 3, ID_FIELD, SortOrder.ASC, null), "Second page (3 items) sorted by ID ASC", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(1, 3, DESCRIPTION_FIELD, SortOrder.DESC, null), "Second page (3 items) sorted by Description DESC", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(999, 4, ID_FIELD, SortOrder.ASC, null), "Page 999 (should be empty) sorted by ID ASC", true)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 1, DATE_CREATED_FIELD, SortOrder.DESC, null), "Single item per page sorted by DateCreated DESC", false)));
    }

    static Stream<Arguments> filtering() {
        return Stream.of(
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.LESS_THAN, 3L), "ID less than 3", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.GREATER_THAN, 2L), "ID greater than 2", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.LESS_THAN_EQUALS, 2L), "ID less than or equals 2", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.GREATER_THAN_EQUALS, 4L), "ID greater than or equals 4", false)),

                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.EQUALS, 0L), "ID equals 0 (should be empty)", true)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.GREATER_THAN, 1000L), "ID greater than 1000 (should be empty)", true)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(ID_FIELD, FilterOperator.LESS_THAN, 1L), "ID less than 1 (should be empty)", true)),

                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DESCRIPTION_FIELD, FilterOperator.LESS_THAN, DESCRIPTION_PREFIX + "3"), "Description less than 'description - 3'", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DESCRIPTION_FIELD, FilterOperator.GREATER_THAN, DESCRIPTION_PREFIX + "1"), "Description greater than 'description - 1'", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DESCRIPTION_FIELD, FilterOperator.LESS_THAN_EQUALS, DESCRIPTION_PREFIX + "2"), "Description less than or equals 'description - 2'", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DESCRIPTION_FIELD, FilterOperator.GREATER_THAN_EQUALS, DESCRIPTION_PREFIX + "4"), "Description greater than or equals 'description - 4'", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DESCRIPTION_FIELD, FilterOperator.EQUALS, "nonexistent"), "Description equals 'nonexistent' (should be empty)", true)),

                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.EQUALS, OrderStatus.CREATED.name()), "Status equals CREATED", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.EQUALS, OrderStatus.SHIPPED.name()), "Status equals SHIPPED", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.EQUALS, OrderStatus.ARCHIVED.name()), "Status equals ARCHIVED", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.LESS_THAN, "SHIPPED"), "Status less than SHIPPED (CREATED, ARCHIVED)", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.GREATER_THAN, "CREATED"), "Status greater than CREATED (SHIPPED)", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(STATUS_FIELD, FilterOperator.EQUALS, "NONEXISTENT"), "Status equals NONEXISTENT (should be empty)", true)),

                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.LESS_THAN, ZonedDateTime.now().plusDays(1)), "DateCreated less than tomorrow", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.GREATER_THAN, ZonedDateTime.now().minusDays(1)), "DateCreated greater than yesterday", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.LESS_THAN_EQUALS, ZonedDateTime.now().plusDays(1)), "DateCreated less than or equals tomorrow", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.GREATER_THAN_EQUALS, ZonedDateTime.now().minusDays(1)), "DateCreated greater than or equals yesterday", false)),

                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.LESS_THAN, ZonedDateTime.now().minusDays(10)), "DateCreated less than 10 days ago (should be empty)", true)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(DATE_CREATED_FIELD, FilterOperator.GREATER_THAN, ZonedDateTime.now().plusDays(10)), "DateCreated greater than 10 days from now (should be empty)", true))
        );
    }

    static Stream<Arguments> filteringValidation() {
        return Stream.of(
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(INVALID_FIELD, FilterOperator.EQUALS, "test"), "Invalid field 'invalidField' should throw exception", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(NON_EXISTING_FIELD, FilterOperator.EQUALS, 123L), "Invalid field 'nonExistingField' should throw exception", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(FAKE_FIELD, FilterOperator.GREATER_THAN, ZonedDateTime.now()), "Invalid field 'fakeField' should throw exception", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(USER_ID_FIELD, FilterOperator.EQUALS, 1L), "Filter by excluded field UserId equals 1 should throw exception", false)),
                Arguments.of(new OrderFilteringTestDto(new GenericFilter<>(USER_ID_FIELD, FilterOperator.LESS_THAN, 2L), "Filter by excluded field UserId less than 2 should throw exception", false))
        );
    }

    static Stream<Arguments> sortingValidation() {
        return Stream.of(
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, INVALID_FIELD, SortOrder.ASC, null), "Sort by invalid field 'invalidField' should throw exception", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, NON_EXISTING_FIELD, SortOrder.DESC, null), "Sort by invalid field 'nonExistingField' should throw exception", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, FAKE_FIELD, SortOrder.ASC, null), "Sort by invalid field 'fakeField' should throw exception", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, USER_ID_FIELD, SortOrder.ASC, null), "Sort by excluded field userId ASC should throw exception", false)),
                Arguments.of(new OrderSortingTestDto(new GenericSortingRequest<>(0, 10, USER_ID_FIELD, SortOrder.DESC, null), "Sort by excluded field userId DESC should throw exception", false))
        );
    }

    static List<Order> getOrders() {
        final var orders = new ArrayList<Order>();
        final var ordersToGet = TOTAL_ORDERS - 1;
        for (int i = 0; i < ordersToGet; i++) {
            orders.add(getOrder(i));
        }
        final var otherUserOrder = new Order(
                2L,
                DESCRIPTION_PREFIX,
                ZonedDateTime.now(),
                OrderStatus.SHIPPED.name()
        );
        orders.add(otherUserOrder);
        return orders;
    }

    private static Order getOrder(int index) {
        final var orderStatus = OrderStatus.values()[new Random().nextInt(3)];
        return new Order(
                USER_ID,
                DESCRIPTION_PREFIX + index,
                ZonedDateTime.now().minusDays(5).plusDays(new Random().nextInt(7)),
                orderStatus.name()
        );
    }
}
