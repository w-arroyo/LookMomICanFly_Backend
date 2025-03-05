package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_subcategories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSubcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 10, max = 220, message = "Description length is invalid.")
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategoryId;

}
