package com.etiqa.assessment.purchase.service;

import com.etiqa.assessment.customers.model.Customer;
import com.etiqa.assessment.purchase.model.Purchase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public interface PurchaseService {

    public Mono<Purchase> createPurchase(Purchase purchase);
    public Flux<Purchase> getAllpurchase();
    public Flux<Purchase> getPurchaseByDateRange(LocalDate startDate, LocalDate endDate);
}
