package com.etiqa.assessment.customers.service;

import com.etiqa.assessment.customers.dto.Customer;
import com.etiqa.assessment.customers.repository.CustomerRepository;
import com.etiqa.assessment.exception.EmailAlreadyExistException;
import com.etiqa.assessment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public Flux<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public Flux<Customer> getCustomersByDateRange(LocalDate startDate, LocalDate endDate) {
        return repo.findByCreatedDateBetween(startDate, endDate);
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return repo.findByEmail(customer.getEmail())
                .flatMap(existingCustomer -> Mono.<Customer>error(new EmailAlreadyExistException("Customer", "email", customer.getEmail())))
                .switchIfEmpty(repo.save(customer).cast(Customer.class));
    }

    public Mono<Customer> getCustomerById(long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer", "id", id)));
    }

    public Mono<Customer> updateCustomer(long id, Customer customer) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer", "id", id)))
                .flatMap(existingCustomer -> {
                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setFamilyMembers(customer.getFamilyMembers());
                    existingCustomer.setStatus(customer.getStatus());
                    return repo.save(existingCustomer);
                });
    }

    public Mono<Void> deleteCustomer(long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer", "id", id)))
                .flatMap(existingCustomer -> repo.deleteById(id));
    }
}
