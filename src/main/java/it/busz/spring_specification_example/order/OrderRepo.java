package it.busz.spring_specification_example.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepo extends JpaRepository<Order, Long> {
}
