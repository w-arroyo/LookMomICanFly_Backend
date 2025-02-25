package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private Boolean active=true;

    @Column(name = "full_name",nullable = false)
    private byte [] fullName;

    @Column(nullable = false)
    private byte [] street;

    @Column(name = "zip_code", nullable = false)
    private byte [] zipCode;

    @Column(nullable = false)
    private byte [] city;

    @Column(nullable = false)
    private byte [] country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient
    @NotBlank(message = "Name is mandatory.")
    private String fullNameAsString;

    @Transient
    @NotBlank(message = "Street info is mandatory")
    private String streetAsString;

    @Transient
    @NotBlank(message = "Zip code is mandatory")
    private String zipCodeAsString;

    @Transient
    @NotBlank(message = "City is mandatory")
    private String cityAsString;

    @Transient
    @NotBlank(message = "Country is mandatory")
    private String countryAsString;
}
