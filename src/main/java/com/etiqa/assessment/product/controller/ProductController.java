package com.etiqa.assessment.product.controller;

import com.etiqa.assessment.exception.customException.NoRequestBodyException;
import com.etiqa.assessment.product.model.Products;
import com.etiqa.assessment.product.service.ProductService;
import com.etiqa.assessment.product.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(
        name = "Product Management System",
        description = "This API allows to add new book, update book, delete book, and retrieve book by id/all/by_date range."
)
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Products> createProduct(@RequestBody(required = false) @Valid Products product) {
        if (product == null) {
            logger.info("Product request body is null");
            throw new NoRequestBodyException();
        }
        return service.createProduct(product);
    }

    @PutMapping("/update/{id}")
    public Mono<Products> updateProduct(@PathVariable long id, @RequestBody(required = false) @Valid Products product) {
        if (product == null) {
            logger.info("Product request body is null");
            throw new NoRequestBodyException();
        }
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable long id) {
        return service.deleteProduct(id)
                .map(successMessage -> ResponseEntity.ok(successMessage));
    }

    @GetMapping("/get/{id}")
    public Mono<Products> getProductById(@PathVariable long id) {
        return service.getProductById(id);
    }

    @GetMapping("/get/all")
    public Flux<Products> getAllProducts() {
        return service.getAllProducts();
    }

}
