package it.busz.spring_specification_example.order;

import jakarta.persistence.Entity;

import java.time.ZonedDateTime;

@Entity
class Order {
    private Long id;
    private Long userId;
    private String description;
    private ZonedDateTime dateCreated;
}
