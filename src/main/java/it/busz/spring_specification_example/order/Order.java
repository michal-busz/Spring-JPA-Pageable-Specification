package it.busz.spring_specification_example.order;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String description;
    private ZonedDateTime dateCreated;
    private OrderStatus status;

    public Order(Long userId, String description, ZonedDateTime dateCreated, OrderStatus status) {
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

    public OrderStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }
}
