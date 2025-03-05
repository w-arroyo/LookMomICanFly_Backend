package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Format;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "music")
@DiscriminatorValue("MUSIC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Music extends ProductCategory{

    @Column(name = "format", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Format format;

}
