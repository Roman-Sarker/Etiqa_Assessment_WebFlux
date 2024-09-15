package com.etiqa.assessment.purchase.repository;

import com.etiqa.assessment.purchase.model.Purchase;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends ReactiveCrudRepository<Purchase, Long> {
}
