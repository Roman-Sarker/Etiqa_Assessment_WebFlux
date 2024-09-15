package com.etiqa.assessment.products.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name = "products")
public class Products {
    @Id
    private long id;

    @NotBlank(message = "Book title cannot be empty")
    @Size(min = 1, max = 50, message = "Book Title must be between 1 and 50 characters")
    @Column("book_title")
    private String bookTitle;

    @Min(value = 1, message = "Book quantity must be greater than 0")
    @Column("book_price")
    private float bookPrice;

    @Min(value = 1, message = "Book quantity must be greater than 0")
    @Column("book_quantity")
    private int bookQuantity;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 1, message = "Status should be one character (Y/N)")
    @Column("status")
    private String status;
}


