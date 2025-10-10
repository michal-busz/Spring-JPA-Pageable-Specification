package it.busz.spring_specification_example.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class  GenericFilter<T>{

        @NotEmpty
        String field;
        @NotNull
        FilterOperator operator;
        @NotNull
        T value;

        public String getField() {
                return field;
        }

        public FilterOperator getOperator() {
                return operator;
        }

        public T getValue() {
                return value;
        }

        public GenericFilter(String field, FilterOperator operator, T value) {
                this.field = field;
                this.operator = operator;
                this.value = value;
        }
}
