package com.etiqa.assessment.customers.service;

import com.etiqa.assessment.customers.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
@Service
public interface CustomerService {

    public Mono<Customer> createCustomer(Customer customer);
    public Mono<Customer> updateCustomer(long id, Customer customer);
    public Mono<String> deleteCustomer(long id);
    public Flux<Customer> getAllCustomers();
    public Mono<Customer> getCustomerById(long id);
    public Flux<Customer> getCustomersByDateRange(LocalDate startDate, LocalDate endDate);
}
