package it.busz.spring_specification_example.request;

import java.util.List;

public record GenericResponse<T>(
        Long totalCount,
        List<T> result
) {

}
