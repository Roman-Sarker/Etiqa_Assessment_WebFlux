package com.etiqa.assessment.customers.repository;

import com.etiqa.assessment.customers.dto.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
     Mono<Customer> findByEmail(String email);

     @Query("SELECT * FROM customers WHERE created_date BETWEEN :startDate AND :endDate")
     Flux<Customer> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
