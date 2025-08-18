package com.mahesh.microservice.service;

import org.springframework.boot.SpringApplication;

import com.mahesh.microservice.order.OrderServiceApplication;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
