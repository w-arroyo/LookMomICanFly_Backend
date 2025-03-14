package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class ShippingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "estimated_time")
    @NotNull
    @NotBlank
    private String estimatedTime;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "shipping_company_id")
    @NotNull
    private ShippingCompany shippingCompany;

}
