package it.busz.spring_specification_example.validation;

import it.busz.spring_specification_example.request.FilterOperator;
import it.busz.spring_specification_example.request.GenericFilter;
import it.busz.spring_specification_example.request.GenericSortingRequest;
import it.busz.spring_specification_example.request.SortOrder;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

final class FieldValidatorTestDataProvider {

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    private static final String TEST_VALUE = "test";

    private FieldValidatorTestDataProvider() {
    }

    static Stream<Arguments> validFiltering() {
        return Stream.of(
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("id", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by id field should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("name", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by name field should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("description", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by description field should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("status", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by status field should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("publicField", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by publicField should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(
                                new GenericFilter<>("id", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("name", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("description", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("status", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("publicField", FilterOperator.EQUALS, TEST_VALUE)
                        ),
                        "Multiple valid filters should be allowed"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(),
                        "Empty filter list should be allowed"
                ))
        );
    }

    static Stream<Arguments> invalidFiltering() {
        return Stream.of(
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("internalNote", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by excluded field internalNote should throw exception"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("sensitiveData", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by excluded field sensitiveData should throw exception"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(
                                new GenericFilter<>("internalNote", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("sensitiveData", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("description", FilterOperator.EQUALS, TEST_VALUE),
                                new GenericFilter<>("status", FilterOperator.EQUALS, TEST_VALUE)
                        ),
                        "Multiple filters with excluded fields should throw exception"
                )),
                Arguments.of(new FieldValidatorFilteringTestDto(
                        List.of(new GenericFilter<>("nonExistentField", FilterOperator.EQUALS, TEST_VALUE)),
                        "Filter by non-existent field should throw exception"
                ))
        );
    }

    static Stream<Arguments> validSorting() {
        return Stream.of(
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "name", SortOrder.ASC, TEST_VALUE),
                        "Sort by valid field name should be allowed"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "id", SortOrder.DESC, TEST_VALUE),
                        "Sort by valid field id should be allowed"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "description", SortOrder.ASC, TEST_VALUE),
                        "Sort by valid field description should be allowed"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "status", SortOrder.DESC, TEST_VALUE),
                        "Sort by valid field status should be allowed"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "publicField", SortOrder.ASC, TEST_VALUE),
                        "Sort by valid field publicField should be allowed"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "createdAt", SortOrder.DESC, TEST_VALUE),
                        "Sort by valid field createdAt should be allowed"
                ))
        );
    }

    static Stream<Arguments> invalidSorting() {
        return Stream.of(
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "internalNote", SortOrder.ASC, TEST_VALUE),
                        "Sort by excluded field internalNote should throw exception"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "sensitiveData", SortOrder.DESC, TEST_VALUE),
                        "Sort by excluded field sensitiveData should throw exception"
                )),
                Arguments.of(new FieldValidatorSortingTestDto(
                        new GenericSortingRequest<>(PAGE_NUMBER, PAGE_SIZE, "nonExistentField", SortOrder.ASC, TEST_VALUE),
                        "Sort by non-existent field should throw exception"
                ))
        );
    }
}