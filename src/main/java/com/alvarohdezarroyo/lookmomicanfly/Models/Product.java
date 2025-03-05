package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    @Size(min = 10, max = 220, message = "Name length is invalid.")
    private String name;

    @Column(name = "launch_year", nullable = false)
    @Min(value = 1950)
    @Max(value = Calendar.YEAR+1)
    private Integer launchYear;

    @Column(name = "active", nullable = false)
    private Boolean active=true;

    @Column(name = "product_category_id", nullable = false)
    private Integer productCategoryId;

    @ManyToOne(fetch = FetchType.EAGER) // loads up the manufacturer of the product automatically
    @JoinColumn(name = "manufactured_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<Color> colors;

}
