package com.mahesh.microservice.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahesh.microservice.order.dto.OrderRequest;
import com.mahesh.microservice.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private ObjectMapper mapper = new ObjectMapper();


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
    	logger.info("order controller save call triggered");
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}