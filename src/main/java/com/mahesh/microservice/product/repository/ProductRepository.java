package com.mahesh.microservice.product.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mahesh.microservice.product.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}