package com.etiqa.assessment.purchase.service;

import com.etiqa.assessment.customers.model.Customer;
import com.etiqa.assessment.customers.repository.CustomerRepository;
import com.etiqa.assessment.exception.customException.InsufficientProductQuantityException;
import com.etiqa.assessment.exception.customException.ResourceNotFoundException;
import com.etiqa.assessment.products.repository.ProductRepository;
import com.etiqa.assessment.purchase.model.Purchase;
import com.etiqa.assessment.purchase.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Component
public class PurchaseServiceImpl implements PurchaseService{

    // Initialize the Logger
    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private PurchaseRepository purchaseRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;

    public Mono<Purchase> createPurchase(Purchase purchase) {
        return customerRepo.findById(purchase.getCustomerId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer", "id", purchase.getCustomerId())))
                .then(productRepo.findById(purchase.getProductId())
                        .flatMap(product -> {
                            if (product.getBookQuantity() < purchase.getQuantity()) {
                                return Mono.error(new InsufficientProductQuantityException("product", product.getId(), product.getBookQuantity()));
                            }
                            return Mono.just(product);
                        })
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product", "id", purchase.getProductId()))))
                .then(purchaseRepo.save(purchase))
                .doOnSuccess(savedPurchase -> {
                    logger.info("Purchase created successfully with ID: {}", savedPurchase.getId());
                })
                .doOnError(error -> {
                    logger.error("Error creating purchase: {}", error.getMessage());
                });
    }
    
    @Override
    public Flux<Purchase> getAllpurchase() {
        return purchaseRepo.findAll()
                .doOnSubscribe(subscription -> logger.info("Started fetching all purchase"))
                .doOnComplete(() -> logger.info("Successfully fetched all purchase"))
                .doOnError(e -> logger.error("Failed to fetch all purchase. Error: {}", e.getMessage()));
    }


    @Override
    public Flux<Purchase> getPurchaseByDateRange(LocalDate startDate, LocalDate endDate) {
        return purchaseRepo.findByCreatedDateBetween(startDate, endDate)
                .collectList()  // Collect the Flux into a List to count total records
                .flatMapMany(purchase -> {
                    logger.info("Total records found between {} and {}: {}", startDate, endDate, purchase.size());
                    return Flux.fromIterable(purchase);  // Return the Flux from the List
                })
                .doOnError(e -> logger.error("Failed to fetch customers by date range ({} to {}). Error: {}", startDate,endDate, e.getMessage()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Purchase","date between "+startDate.toString()+" and "+endDate.toString(),null)));
    }

}
