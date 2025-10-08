package it.busz.spring_specification_example.order;

final class OrderTestConstants {

    // Field name constants
    static final String ID_FIELD = "id";
    static final String USER_ID_FIELD = "userId";
    static final String DESCRIPTION_FIELD = "description";
    static final String STATUS_FIELD = "status";
    static final String DATE_CREATED_FIELD = "dateCreated";

    // Test utilities constants
    static final int TOTAL_ORDERS = 100;
    static final Long USER_ID = 1L;
    static final String DESCRIPTION_PREFIX = "description - ";

    // Assertion message constants
    static final String RESPONSE_NULL_ERROR = "Response result should not be null for: ";
    static final String TOTAL_COUNT_NULL_ERROR = "Total count should not be null for: ";
    static final String TOTAL_COUNT_MATCH_ERROR = "Total count should match expected for: ";
    static final String TOTAL_COUNT_NON_NEGATIVE_ERROR =
            "Total count should be non-negative for: ";
    static final String USER_FILTER_ERROR =
            "All results should belong to user " + USER_ID + " for: ";
    static final String PAGE_SIZE_ERROR = "Page size should match expected for: ";
    static final String ASC_SORT_ERROR = "ASC sorting failed: %s should be <= %s for: %s";
    static final String DESC_SORT_ERROR = "DESC sorting failed: %s should be >= %s for: %s";
    static final String FILTER_ERROR = "%s filter failed for: %s";

    private OrderTestConstants() {
    }
}
