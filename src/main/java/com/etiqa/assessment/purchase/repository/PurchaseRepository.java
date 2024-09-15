package com.etiqa.assessment.purchase.repository;

import com.etiqa.assessment.purchase.model.Purchase;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface PurchaseRepository extends ReactiveCrudRepository<Purchase, Long> {
    @Query("SELECT * FROM purchases WHERE created_date BETWEEN :startDate AND :endDate")
    Flux<Purchase> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
