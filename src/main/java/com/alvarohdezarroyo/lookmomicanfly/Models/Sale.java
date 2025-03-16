package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sales")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale extends Transaction{

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private SaleStatus status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_and_sales",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "tracking_number_id")
    )
    @NotNull
    private List<TrackingNumber> trackingNumbers;

}
