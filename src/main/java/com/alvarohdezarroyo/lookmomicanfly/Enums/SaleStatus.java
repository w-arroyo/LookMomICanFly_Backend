package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum SaleStatus {

    PENDING("You haven't shipped the product yet."),
    SHIPPED("Your product is on route to us."),
    RECEIVED("We have your product."),
    VERIFYING("We are authenticating your product."),
    FAILED("Your product is not authentic and failed the process."),
    COMPLETED("Your sale was completed and we processed your payout"),
    CANCELLED("Your sale was cancelled.");

    final String value;

    SaleStatus(String value){
        this.value=value;
    }

    public static SaleStatus checkStatus(String check){
        for(SaleStatus status: SaleStatus.values()){
            if(status.name().equalsIgnoreCase(check))
                return status;
        }
        throw new EntityNotFoundException("Sale status does not exist.");

    }

}
