package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.ContainerTest;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.request.SortOrder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static it.busz.spring_specification_example.order.OrderTestConstants.ID_FIELD;
import static it.busz.spring_specification_example.order.OrderTestConstants.USER_ID;
import static it.busz.spring_specification_example.order.OrderVerificationHelper.verifyFiltering;
import static it.busz.spring_specification_example.order.OrderVerificationHelper.verifySorting;

class OrderServiceTest extends ContainerTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepo orderRepo;

    @BeforeAll
    void prepareTest() {
        final var orders = OrderTestDataProvider.getOrders();
        orderRepo.saveAll(orders);
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.order.OrderTestDataProvider#sortingWithPagination")
    void searchSortingWithPagination(@NotNull OrderSortingTestDto testDto) {
        // given
        final var request = testDto.request();

        // when
        final var response = orderService.searchSorting(request);

        // then
        verifySorting(response, request, testDto);
    }

    @ParameterizedTest
    @MethodSource("it.busz.spring_specification_example.order.OrderTestDataProvider#filtering")
    void searchFiltering(@NotNull OrderFilteringTestDto testDto) {
        // given
        final var filters = new OrderListRequest(List.of(testDto.filter()));
        final var request = new GenericSortingRequest<>(0, 10, ID_FIELD, SortOrder.ASC, filters);

        // when
        final var response = orderService.searchFiltering(request, USER_ID);

        // then
        verifyFiltering(response, request, testDto);
    }

}
