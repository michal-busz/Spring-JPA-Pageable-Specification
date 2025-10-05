package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.ContainerTest;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.request.SortOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest extends ContainerTest {
    private final static String ID_FIELD = "id";
    private final static int PAGE_SIZE = 5;
    private final static int TOTAL_ORDERS = 6;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepo orderRepo;

    @BeforeAll
    void prepareTest() {
        final var orders = getNOrders(TOTAL_ORDERS);
        orderRepo.saveAll(orders);
    }

    @Test
    void sortDescendingById() {
        // given
        final var request = new GenericSortingRequest<Void>(0, 5, ID_FIELD, SortOrder.DESC, null);

        // when
        final var response = orderService.findSorting(request);

        // then
        assertNotNull(response.result());
        final var result = response.result();
        assertFalse(result.isEmpty());
        assertEquals(PAGE_SIZE, result.size());
        assertEquals(TOTAL_ORDERS, response.totalCount());

        assertEquals(6L, result.getFirst().id());
        assertEquals(2L, result.getLast().id());
    }

    @Test
    void sortAscendingById() {
        // given
        final var request = new GenericSortingRequest<Void>(0, 5, ID_FIELD, SortOrder.ASC, null);

        // when
        final var response = orderService.findSorting(request);

        // then
        assertNotNull(response.result());
        final var result = response.result();
        assertFalse(result.isEmpty());
        assertEquals(PAGE_SIZE, result.size());
        assertEquals(TOTAL_ORDERS, response.totalCount());

        assertEquals(1L, result.getFirst().id());
        assertEquals(5L, result.getLast().id());
    }

    @Test
    void sortDescendingByDateCreated() {
        // given
        final var request = new GenericSortingRequest<Void>(0, 5, "dateCreated", SortOrder.DESC, null);

        // when
        final var response = orderService.findSorting(request);

        // then
        assertNotNull(response.result());
        final var result = response.result();
        assertFalse(result.isEmpty());
        assertEquals(PAGE_SIZE, result.size());
        assertEquals(TOTAL_ORDERS, response.totalCount());

        assertEquals(6L, result.getFirst().id());
        assertEquals(2L, result.getLast().id());
    }

    private Order getOrder(int index) {
        final var statusIndex = (index % 3);
        final var orderStatus = OrderStatus.values()[Math.toIntExact(statusIndex)];
        return new Order(
                (long) index,
                "description - " + index,
                ZonedDateTime.now().plusSeconds(index),
                orderStatus
        );
    }

    private List<Order> getNOrders(int n) {
        final var orders = new ArrayList<Order>();
        for (int i = 0; i < n; i++) {
            orders.add(getOrder(i));
        }
        return orders;
    }
}
