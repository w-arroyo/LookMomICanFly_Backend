package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collectibles")
@DiscriminatorValue("COLLECTIBLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collectible extends Product{

    @Column(name = "length", nullable = false)
    private Integer length;

}
