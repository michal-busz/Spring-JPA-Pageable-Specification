# Spring JPA Specification Example

This repository contains a practical example of implementing generic JPA Specifications in Spring for dynamic filtering, sorting, and pagination. The framework can be reused across different entities while maintaining type safety.

[Check out my blog post for detailed explanations.](https://busz.it/spring-jpa-specification-and-pageable-filtering-sorting-pagination/)
## Overview

This project demonstrates a flexible specification framework that allows you to:
- **Dynamically filter** entities using various operators (EQUALS, LESS_THAN, GREATER_THAN, etc.)
- **Sort** results by any field with configurable direction (ASC/DESC)
- **Paginate** results using Spring Data's Pageable
- Maintain **type safety** through compile-time validation
- Apply the framework to **any JPA entity**

## Features

1. **Generic Filtering**
   - Data types: `Long`, `String`, `ZonedDateTime`
   - Type-safe field validation
   - Configurable allowed fields per entity
   - Chainable filter specifications

2. **Flexible Sorting**
   - Sort by any entity field
   - Configurable sort direction
   - Field validation against allowed fields

3. **Pagination**
   - Standard Spring Data `Pageable` integration
   - Configurable page size and number

## Core Components

- **`GenericSpecification`**: Main specification builder that creates JPA criteria queries
- **`GenericFilter`**: Represents a single filter condition (field, operator, value)
- **`FilterOperator`**: Enum with supported comparison operators
- **`GenericSortingRequest`**: Handles sorting and pagination parameters
- **`GenericResponse`**: Standardized response format for paginated results

## Example Usage

### API Usage Example

```java
final var statusFilter = new GenericFilter("status", FilterOperator.EQUALS, "PENDING");
final var dateFilter = new GenericFilter("dateCreated", FilterOperator.GREATER_THAN, someDate);
final var request = new GenericSortingRequest<>(
        pageNumber,
        pageSize,
        sortBy,
        SortOrder.DESC,
        new OrderListRequest(List.of(statusFilter, dateFilter))
);

Pageable pageable = request.toPageable();
Specification<Order> filtersSpec = GenericSpecification.<Order>getFiltersSpecification(request.object().filters());
List<Order> orders = orderRepo.findAll(filtersSpec, pageable);
```

### Custom Specifications

You can combine custom business logic with dynamic filters:

```java
private Specification<Order> getSpecificationForSearch(List<GenericFilter> filters, Long userId) {
   Specification<Order> customSpec = Specification.allOf(
           OrderSpecification.forUser(userId),
           OrderSpecification.dateCreatedAfterFiveDaysToNow()
   );
   
   Specification<Order> filtersSpec = GenericSpecification.<Order>getFiltersSpecification(filters, ALLOWED_FILTER_FIELDS);

   return Specification.allOf(customSpec, filtersSpec);
}
```

## Supported Filter Operators

- `=`
- `>`
- `>=`
- `<`
- `<=`

## Supported Data Types

- `Long`
- `String`
- `ZonedDateTime`

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL (for runtime)

## Building and Running

### Build the project
```bash
mvn clean compile
```

### Tests
The project uses Testcontainers for integration testing with a real PostgreSQL database. Tests cover filtering, sorting, pagination, error handling, and type validation.

Run tests with:
```bash
mvn test
```

### Run the application
Provide valid data source in application.properties
```bash
mvn spring-boot:run
```

## CI/CD

GitHub Actions workflow is configured to:
- Build on push/PR to `master` and `dev` branches
- Run tests with JDK 21
- Cache Maven dependencies for faster builds