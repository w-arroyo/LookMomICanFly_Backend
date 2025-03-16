package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "ask_id")
    @NotNull
    private Ask ask;

    @OneToOne
    @JoinColumn(name = "bid_id")
    @NotNull
    private Bid bid;

    @Column (name = "date", updatable = false)
    @NotNull
    private LocalDateTime date= LocalDateTime.now();

}
