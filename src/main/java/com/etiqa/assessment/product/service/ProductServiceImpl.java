package com.etiqa.assessment.product.service;

import com.etiqa.assessment.exception.customException.ResourceNotFoundException;
import com.etiqa.assessment.product.model.Products;
import com.etiqa.assessment.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProductServiceImpl implements ProductService{

    // Initialize the Logger
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository repo;

    public Mono<Products> createProduct(Products product) {
        return repo.save(product)
                .doOnSuccess(createdProduct -> logger.info("Customer created successfully with ID: {}", createdProduct.getId()))
                .doOnError(e -> logger.error("Failed to create product. Error: {}", e.getMessage()));
    }

    @Override
    public Mono<Products> updateProduct(long id, Products product) {
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Product not found with ID: {}", id);  // Log when product is not found
                    return Mono.error(new ResourceNotFoundException("Product", "id", id));
                }))
                .flatMap(existingProduct -> {
                    logger.info("Existing product found: {}", existingProduct);
                    existingProduct.setBookTitle(product.getBookTitle());
                    existingProduct.setBookPrice(product.getBookPrice());
                    existingProduct.setBookQuantity(product.getBookQuantity());
                    existingProduct.setStatus(product.getStatus());
                    return repo.save(existingProduct)
                            .doOnSuccess(updatedProduct -> logger.info("Product updated successfully with ID: {}", id))
                            .doOnError(e -> logger.error("Failed to update product with ID: {}. Error: {}", id, e.getMessage()));
                });
    }

    @Override
    public Mono<String> deleteProduct(long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Product not found with ID: {}", id);  // Log when product is not found
                    return Mono.error(new ResourceNotFoundException("Product", "id", id));
                }))
                .flatMap(existingProduct -> repo.deleteById(id)
                        .then(Mono.fromSupplier(() -> {
                            String successMessage = "Product deleted successfully with ID: " + id;
                            logger.info(successMessage);
                            return successMessage;
                        }))
                        .doOnError(e -> logger.error("Failed to delete product with ID: {}. Error: {}", id, e.getMessage()))
                );
    }

    @Override
    public Flux<Products> getAllProducts() {
        return repo.findAll()
                .doOnSubscribe(subscription -> logger.info("Started fetching all products"))
                .doOnComplete(() -> logger.info("Successfully fetched all products"))
                .doOnError(e -> logger.error("Failed to fetch all products. Error: {}", e.getMessage()));
    }

    @Override
    public Mono<Products> getProductById(long id) {
        return repo.findById(id)
                .doOnSuccess(product -> {
                    if (product != null) {
                        logger.info("Product fetched successfully: {}", product);
                    }else {
                        logger.warn("Product not found with id : "+id);
                    }
                })
                .doOnError(e -> logger.error("Failed to fetch product with ID: {}. Error: {}", id, e.getMessage()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product", "id", id)));
    }

}
