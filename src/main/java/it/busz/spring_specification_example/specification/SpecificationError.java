package it.busz.spring_specification_example.specification;

public enum SpecificationError {
    INVALID_FILTER_FIELD("invalid-filter-field"),
    INVALID_FILTER_VALUE("invalid-filter-value");

    public String getErrorCode() {
        return errorCode;
    }

    private final String errorCode;

    SpecificationError(String errorCode) {
        this.errorCode = errorCode;
    }
}
