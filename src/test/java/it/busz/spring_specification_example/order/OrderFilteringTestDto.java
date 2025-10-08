package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericFilter;

record OrderFilteringTestDto(
        GenericFilter filter,
        String testDescription,
        boolean shouldBeEmpty
) {
}
