package com.alvarohdezarroyo.lookmomicanfly.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    private String userId, productId, size, addressId;
    private Integer amount;

}
