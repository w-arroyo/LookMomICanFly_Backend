package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ask extends Post {

    @JoinColumn(name = "fee_id")
    @NotNull
    private SellingFee sellingFee;

    @Column(name = "shipping_fee")
    @NotNull
    private Double shippingFee;

    // add shipping fee to app properties

}
