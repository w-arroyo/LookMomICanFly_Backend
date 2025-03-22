package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

    private String reference,size,trackingNumber;
    private AddressDTO address;
    private ProductSummaryDTO product;
    private TransactionStatusDTO status;
    private int amount;

}
