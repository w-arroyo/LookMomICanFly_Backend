package com.alvarohdezarroyo.lookmomicanfly.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidRequest extends PostRequest {

    private String shippingOptionId;

}
