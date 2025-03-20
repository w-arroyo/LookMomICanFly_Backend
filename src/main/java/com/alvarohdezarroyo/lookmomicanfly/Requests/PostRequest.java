package com.alvarohdezarroyo.lookmomicanfly.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private String userId, productId, size, addressId;
    private Integer amount;

}
