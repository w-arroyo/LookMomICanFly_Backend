package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tracking_numbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "code")
    @NotNull
    @NotBlank
    private String code;

    @Column(name = "used")
    @NotNull
    private Boolean used;

}
