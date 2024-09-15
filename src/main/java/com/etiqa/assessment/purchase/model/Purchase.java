package com.etiqa.assessment.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@Table(name = "Purchases")
public class Purchase {
    @Id
    private long id;

    @NotNull
    @Column("customer_id")
    private long customerId;

    @NotNull
    @Column("product_id")
    private long productId;

    @Min(value = 1, message = "Price must be greater than 0")
    @Column("price")
    private float price;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @Column("quantity")
    private int quantity;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 1, message = "Status should be one character (Y/N)")
    @Column("status")
    private String status;

    @Column("created_date")
    private LocalDate purchaseDate;

}


