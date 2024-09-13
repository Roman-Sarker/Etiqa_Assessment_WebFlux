package com.etiqa.assessment.customers.controller;

import com.etiqa.assessment.customers.dto.Customer;
import com.etiqa.assessment.customers.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Customer> getCustomerById(@PathVariable long id) {
        return service.getCustomerById(id);
    }

    @GetMapping("/get/by-date")
    public Flux<Customer> getCustomersByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, //RRRR/MM/DD
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        System.out.println("startDate : "+startDate);
        return service.getCustomersByDateRange(startDate, endDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/all")
    public Flux<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }



    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable long id) {
        return service.deleteCustomer(id);
    }
}
