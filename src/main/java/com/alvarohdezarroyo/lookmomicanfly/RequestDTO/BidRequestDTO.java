package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidRequestDTO extends PostRequestDTO {

    private String shippingOptionId;
    private String userId, productId, size, addressId;
    private Integer amount;

}
