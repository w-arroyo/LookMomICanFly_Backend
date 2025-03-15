package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {



    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;

    @Column(name = "size")
    @NotNull
    private Size size;

    @Column(name = "amount")
    @Min(value = 1)
    @NotNull
    private Integer amount;

    @Column(name = "active")
    @NotNull
    private Boolean active=true;

    @Column(name = "finalized")
    @NotNull
    private Boolean finalized=false;


}
