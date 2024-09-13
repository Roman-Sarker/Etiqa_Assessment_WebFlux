package com.etiqa.assessment.customers.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name = "customers")
public class Customer {
    @Id
    private long id;


    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column("first_name")
    private String firstName;

    @NotBlank(message = "Family members cannot be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column("last_name")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column("email")
    private String email;

    @Column("family_members")
    private String familyMembers;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 1, message = "Status should be one character (Y/N)")
    @Column("status")
    private String status;
}


