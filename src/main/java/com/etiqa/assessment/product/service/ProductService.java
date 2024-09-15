package com.etiqa.assessment.product.service;

import com.etiqa.assessment.product.model.Products;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface ProductService {

    public Mono<Products> createProduct(Products product);
    public Mono<Products> updateProduct(long id, Products product);
    public Mono<String> deleteProduct(long id);
    public Flux<Products> getAllProducts();
    public Mono<Products> getProductById(long id);
}
