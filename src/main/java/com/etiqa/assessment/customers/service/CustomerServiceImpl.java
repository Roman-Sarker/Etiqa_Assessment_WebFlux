package com.etiqa.assessment.customers.service;

import com.etiqa.assessment.customers.dto.Customer;
import com.etiqa.assessment.customers.repository.CustomerRepository;
import com.etiqa.assessment.exception.EmailAlreadyExistException;
import com.etiqa.assessment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomerServiceImpl implements CustomerService{

    // Initialize the Logger
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository repo;

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return repo.findByEmail(customer.getEmail())
                .flatMap(existingCustomer -> {
                    logger.info("Customer already exist with email : {}",customer.getEmail());
                    return Mono.error(new EmailAlreadyExistException("Customer", "email", customer.getEmail())).cast(Customer.class);
                })
                .switchIfEmpty(repo.save(customer).cast(Customer.class)
                        .doOnSuccess(createdCustomer -> logger.info("Customer created successfully with ID: {}", createdCustomer.getId()))
                        .doOnError(e -> logger.error("Failed to create customer. Error: {}", e.getMessage()))
                );
    }

    @Override
    public Mono<Customer> updateCustomer(long id, Customer customer) {
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Customer not found with ID: {}", id);  // Log when customer is not found
                    return Mono.error(new ResourceNotFoundException("Customer", "id", id));
                }))
                .flatMap(existingCustomer -> {
                    logger.info("Existing customer found: {}", existingCustomer);
                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setFamilyMembers(customer.getFamilyMembers());
                    existingCustomer.setStatus(customer.getStatus());
                    return repo.save(existingCustomer)
                            .doOnSuccess(updatedCustomer -> logger.info("Customer updated successfully with ID: {}", id))
                            .doOnError(e -> logger.error("Failed to update customer with ID: {}. Error: {}", id, e.getMessage()));
                });
    }

    @Override
    public Mono<String> deleteCustomer(long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Customer not found with ID: {}", id);  // Log when customer is not found
                    return Mono.error(new ResourceNotFoundException("Customer", "id", id));
                }))
                .flatMap(existingCustomer -> repo.deleteById(id)
                        .then(Mono.fromSupplier(() -> {
                            String successMessage = "Customer deleted successfully with ID: " + id;
                            logger.info(successMessage);
                            return successMessage;
                        }))
                        .doOnError(e -> logger.error("Failed to delete customer with ID: {}. Error: {}", id, e.getMessage()))
                );
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return repo.findAll()
                .doOnSubscribe(subscription -> logger.info("Started fetching all customers"))
                .doOnComplete(() -> logger.info("Successfully fetched all customers"))
                .doOnError(e -> logger.error("Failed to fetch all customers. Error: {}", e.getMessage()));
    }

    @Override
    public Flux<Customer> getCustomersByDateRange(LocalDate startDate, LocalDate endDate) {
        return repo.findByCreatedDateBetween(startDate, endDate)
                .collectList()  // Collect the Flux into a List to count total records
                .flatMapMany(customers -> {
                    logger.info("Total records found between {} and {}: {}", startDate, endDate, customers.size());
                    return Flux.fromIterable(customers);  // Return the Flux from the List
                })
                .doOnError(e -> logger.error("Failed to fetch customers by date range ({} to {}). Error: {}", startDate,endDate, e.getMessage()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer","date between "+startDate.toString()+" and "+endDate.toString(),null)));
    }

    @Override
    public Mono<Customer> getCustomerById(long id) {
        return repo.findById(id)
                .doOnSuccess(customer -> {
                    if (customer != null) {
                        logger.info("Customer fetched successfully: {}", customer);
                    }else {
                        logger.warn("Customer not found with id : "+id);
                    }
                })
                .doOnError(e -> logger.error("Failed to fetch customer with ID: {}. Error: {}", id, e.getMessage()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer", "id", id)));
    }

}
