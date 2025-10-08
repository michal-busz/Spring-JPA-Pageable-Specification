package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.request.GenericSortingRequest;

record OrderSortingTestDto(
        GenericSortingRequest<Void> request,
        String testDescription,
        boolean shouldBeEmpty
) {
}
