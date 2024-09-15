package com.etiqa.assessment.products.repository;

import com.etiqa.assessment.products.model.Products;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Products, Long> {
}
