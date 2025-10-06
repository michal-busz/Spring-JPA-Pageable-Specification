package it.busz.spring_specification_example.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
