package com.fds.orderservice.repository;

import com.fds.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    @Override
    void deleteById(Long id);

    void deleteByOrderNumber(String orderNumber);
}
