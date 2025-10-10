package it.busz.spring_specification_example.order;

import it.busz.spring_specification_example.specification.validation.FilterExclude;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @FilterExclude
    private Long userId;
    private String description;
    private ZonedDateTime dateCreated;
    private String status;

    public Order(Long userId, String description, ZonedDateTime dateCreated, String status) {
        this.userId = userId;
        this.description = description;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Order() {
    }

    public Long getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }
}
