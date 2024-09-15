package com.etiqa.assessment.product.repository;

import com.etiqa.assessment.product.model.Products;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Products, Long> {
     @Query("SELECT * FROM products WHERE created_date BETWEEN :startDate AND :endDate")
     Flux<Products> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
