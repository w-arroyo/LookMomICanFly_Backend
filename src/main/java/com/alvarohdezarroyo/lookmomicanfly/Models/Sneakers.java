package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sneakers")
@DiscriminatorValue("SNEAKERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sneakers extends ProductCategory {

    @Column(name = "sku", unique = true, nullable = false)
    @NotBlank
    private String sku;

}
