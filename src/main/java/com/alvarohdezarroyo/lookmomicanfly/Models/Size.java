package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "size", nullable = false, unique = true)
    @NotBlank
    private String size;

}
