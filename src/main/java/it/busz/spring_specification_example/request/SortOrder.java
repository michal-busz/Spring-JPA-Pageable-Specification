package it.busz.spring_specification_example.request;

import org.springframework.data.domain.Sort;

public enum SortOrder {
    ASC,
    DESC;

    public Sort.Direction toDirection() {
        if (this == SortOrder.ASC) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }
}
