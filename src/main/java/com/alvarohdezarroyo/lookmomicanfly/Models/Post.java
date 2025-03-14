package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "size")
    @NotNull
    private Size size;

    @Column(name = "amount")
    @NotNull
    private Integer amount;

    @Column(name = "active")
    @NotNull
    private Boolean active=true;

    @Column(name = "finalized")
    @NotNull
    private Boolean finalized=false;


}
