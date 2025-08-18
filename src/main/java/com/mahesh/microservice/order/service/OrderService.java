package com.mahesh.microservice.order.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahesh.microservice.order.dto.OrderRequest;
import com.mahesh.microservice.order.entity.Order;
import com.mahesh.microservice.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	private ObjectMapper mapper = new ObjectMapper();

	public void placeOrder(OrderRequest orderRequest) {
		logger.info("order service save call triggered");
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setPrice(orderRequest.price());
		order.setQuantity(orderRequest.quantity());
		order.setSkuCode(orderRequest.skuCode());
		orderRepository.save(order);
	}
}
