package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Email is mandatory.")
    @Email
    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private byte [] name;

    @Column (nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

    @Column (name = "registration_date",nullable = false, updatable = false)
    private LocalDateTime registrationDate= LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false, updatable = false)
    private UserType userType;

    @Transient // this field does not belong to the database
    @NotBlank(message = "Name is mandatory")
    private String nameAsString;

    @Transient
    @Min(value = 1, message = "PK MUST BE POSITIVE")
    @Max(value = 2, message = "PK MUST BE EITHER 1 OR 2")
    private Integer userTypeId;
}
