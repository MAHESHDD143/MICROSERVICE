package com.mahesh.microservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahesh.microservice.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}