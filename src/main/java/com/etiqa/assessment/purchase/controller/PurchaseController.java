package com.etiqa.assessment.purchase.controller;

import com.etiqa.assessment.exception.customException.NoRequestBodyException;
import com.etiqa.assessment.purchase.model.Purchase;
import com.etiqa.assessment.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(
        name = "Purchase product/book",
        description = "This API allows to purchase a book, and retrive all history of purchase."
)
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService service;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Purchase> createpurchase(@RequestBody(required = false) @Valid Purchase purchase) {
        if (purchase == null) {
            logger.info("purchase request body is null");
            throw new NoRequestBodyException();
        }
        return service.createPurchase(purchase);
    }

    @GetMapping("/get/{id}")
    public Mono<Purchase> getPurchaseById(@PathVariable long id) {
        return service.getPurchaseById(id);
    }

    @GetMapping("/get/all")
    public Flux<Purchase> getAllpurchase() {
        return service.getAllpurchase();
    }

}
