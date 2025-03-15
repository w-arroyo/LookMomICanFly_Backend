package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "bids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid extends Post{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "shipping_option_id")
    @NotNull
    private ShippingOption shippingOption;

    @Column(name = "operational_fee")
    @NotNull
    private Double operationalFee;
    // add operational fee to mapper

}
