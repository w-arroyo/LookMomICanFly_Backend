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
@Table(name = "skateboards")
@DiscriminatorValue("SKATEBOARDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skateboard extends ProductCategory{

    @Column(name = "", nullable = false)
    @NotBlank
    private String collectionName;

}
