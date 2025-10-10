package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.FilterOperator;
import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericResponse;
import it.busz.spring_specification_example.request.GenericSortingRequest;

import java.time.ZonedDateTime;

import static it.busz.spring_specification_example.order.OrderTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

final class OrderVerificationHelper {

    private OrderVerificationHelper() {
    }

    static void verifySorting(
            GenericResponse<OrderDto> response, GenericSortingRequest<Void> request, OrderSortingTestDto testDto) {
        final var testData = new TestData(response, request, testDto.testDescription(), testDto.shouldBeEmpty());
        verifyResponse(testData);
        verifySorting(testData);
    }

    static void verifyFiltering(
            GenericResponse<OrderDto> response, GenericSortingRequest<OrderListRequest> request, OrderFilteringTestDto testDto) {
        final var testData = new TestData(response, request, testDto.testDescription(), testDto.shouldBeEmpty());
        verifyResponse(testData);
        verifyFiltering(testData);
    }

    private static void verifyResponse(TestData testDto) {
        final var testDescription = testDto.description();
        final var result = testDto.response().result();
        final var totalCount = testDto.response().totalCount();

        assertNotNull(result, RESPONSE_NULL_ERROR.concat(testDescription));
        assertNotNull(totalCount, TOTAL_COUNT_NULL_ERROR.concat(testDescription));

        if (testDto.shouldBeEmpty()) {
            assertTrue(result.isEmpty(), TOTAL_COUNT_MATCH_ERROR.concat(testDescription));
        } else {
            assertFalse(result.isEmpty(), TOTAL_COUNT_MATCH_ERROR.concat(testDescription));
        }
    }

    private static void verifyFiltering(TestData testDto) {
        final var testDescription = testDto.description();
        final var response = testDto.response();

        if (!testDto.shouldBeEmpty()) {
            assertTrue(response.totalCount() > 0, TOTAL_COUNT_NON_NEGATIVE_ERROR.concat(testDescription));
        }
        assertTrue(
                response.result().stream().allMatch(o -> o.userId().equals(USER_ID)),
                USER_FILTER_ERROR.concat(testDescription));
        final var filter = ((OrderListRequest) testDto.request().object()).filters().getFirst();
        response.result().forEach(order -> verifyOrderMatchesFilter(order, filter, testDescription));
    }

    private static <T> int calculateExpectedPageSize(GenericSortingRequest<T> request) {
        return Math.min(request.pageSize(), Math.max(0, TOTAL_ORDERS - (request.pageSize() * request.pageNumber())));
    }

    private static void verifySorting(TestData testDto) {
        final var results = testDto.response().result();
        final var sortBy = testDto.request().sortBy();
        final var testDescription = testDto.description();

        assertEquals(TOTAL_ORDERS, testDto.response().totalCount().intValue(), TOTAL_COUNT_MATCH_ERROR.concat(testDescription));
        final var expectedSize = calculateExpectedPageSize(testDto.request());
        assertEquals(expectedSize, results.size(), PAGE_SIZE_ERROR.concat(testDescription));

        for (int i = 0; i < results.size() - 1; i++) {
            final var current = results.get(i);
            final var next = results.get(i + 1);
            verifyPairSortingOrder(current, next, sortBy, testDto);
        }
    }

    private static void verifyPairSortingOrder(
            OrderDto current, OrderDto next, String sortBy, TestData testDto) {
        switch (sortBy) {
            case ID_FIELD -> compare(current.id(), next.id(), testDto);
            case USER_ID_FIELD -> compare(current.userId(), next.userId(), testDto);
            case DESCRIPTION_FIELD -> compare(current.description(), next.description(), testDto);
            case STATUS_FIELD -> compare(current.status(), next.status(), testDto);
            case DATE_CREATED_FIELD -> compare(current.dateCreated(), next.dateCreated(), testDto);
            default -> throw new IllegalArgumentException("Field not supported");
        }
    }

    private static <T extends Comparable<T>> void compare(
            T currentValue, T nextValue, TestData testDto) {
        final var comparison = currentValue.compareTo(nextValue);
        switch (testDto.request().sortOrder()) {
            case ASC -> assertTrue(
                    comparison <= 0, String.format(ASC_SORT_ERROR, currentValue, nextValue, testDto.description()));
            case DESC -> assertTrue(
                    comparison >= 0, String.format(DESC_SORT_ERROR, currentValue, nextValue, testDto.description()));
        }
    }

    private static void verifyOrderMatchesFilter(
            OrderDto order, GenericFilter filter, String testDescription) {
        switch (filter.getField()) {
            case ID_FIELD -> verifyFieldFilter(order.id(), filter.getOperator(), (Long) filter.getValue(), testDescription);
            case USER_ID_FIELD ->
                    verifyFieldFilter(order.userId(), filter.getOperator(), (Long) filter.getValue(), testDescription);
            case DESCRIPTION_FIELD ->
                    verifyFieldFilter(order.description(), filter.getOperator(), (String) filter.getValue(), testDescription);
            case STATUS_FIELD ->
                    verifyFieldFilter(order.status(), filter.getOperator(), (String) filter.getValue(), testDescription);
            case DATE_CREATED_FIELD ->
                    verifyFieldFilter(order.dateCreated(), filter.getOperator(), (ZonedDateTime) filter.getValue(), testDescription);
        }
    }

    private static <T extends Comparable<T>> void verifyFieldFilter(
            T actualValue, FilterOperator operator, T expectedValue, String testDescription) {
        final var errorMessage = String.format(FILTER_ERROR, operator.name(), testDescription);

        switch (operator) {
            case EQUALS -> assertEquals(expectedValue, actualValue, errorMessage);
            case LESS_THAN -> assertTrue(actualValue.compareTo(expectedValue) < 0, errorMessage);
            case GREATER_THAN -> assertTrue(actualValue.compareTo(expectedValue) > 0, errorMessage);
            case LESS_THAN_EQUALS -> assertTrue(actualValue.compareTo(expectedValue) <= 0, errorMessage);
            case GREATER_THAN_EQUALS -> assertTrue(actualValue.compareTo(expectedValue) >= 0, errorMessage);
        }
    }

    private record TestData(
            GenericResponse<OrderDto> response,
            GenericSortingRequest<?> request,
            String description,
            boolean shouldBeEmpty
    ) {

    }
}
