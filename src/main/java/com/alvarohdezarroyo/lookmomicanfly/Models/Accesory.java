package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Material;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accessories")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accesory extends Product {

    @Column(name = "material", nullable = false)
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Material material;

}
