package com.mahesh.microservice.product.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahesh.microservice.product.dto.ProductRequest;
import com.mahesh.microservice.product.dto.ProductResponse;
import com.mahesh.microservice.product.entity.Product;
import com.mahesh.microservice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ObjectMapper mapper = new ObjectMapper();
 
    public ProductResponse createProduct(ProductRequest productRequest) throws JsonProcessingException {
    	logger.debug("product service save call triggered");
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        logger.info("Product {} is saved", product.getId());
        logger.info("product saved {} ",mapper.writeValueAsString(product));
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
    	logger.info("product service get call triggered");
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
    	logger.info("product service get call helper method triggered");
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }
}