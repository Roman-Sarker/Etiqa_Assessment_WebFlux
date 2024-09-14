package com.etiqa.assessment.customers.controller;

import com.etiqa.assessment.customers.model.Customer;
import com.etiqa.assessment.customers.service.CustomerService;
import com.etiqa.assessment.customers.service.CustomerServiceImpl;
import com.etiqa.assessment.exception.NoRequestBodyException;
import com.etiqa.assessment.product.model.Products;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody(required = false) @Valid Customer customer) {
        if (customer == null){
            logger.info("Customer request body is null");
            throw new NoRequestBodyException();
        }
        return service.createCustomer(customer);
    }

    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@PathVariable long id, @RequestBody(required = false) @Valid Customer customer) {
        if (customer == null){
            logger.info("Customer request body is null");
            throw new NoRequestBodyException();
        }
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteCustomer(@PathVariable long id) {
        return service.deleteCustomer(id)
                .map(successMessage -> {  // This will log the success message asynchronously
                    return ResponseEntity.ok(successMessage);  // Return the success message inside the ResponseEntity
                });
    }

    @GetMapping("/get/{id}")
    public Mono<Customer> getCustomerById(@PathVariable long id) {
        return service.getCustomerById(id);
    }

    @GetMapping("/get/by-date")
    public Flux<Customer> getCustomersByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, //RRRR/MM/DD
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return service.getCustomersByDateRange(startDate, endDate);
    }


    @GetMapping("/get/all")
    public Flux<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

}
