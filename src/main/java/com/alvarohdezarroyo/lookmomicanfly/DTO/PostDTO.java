package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private String id, userId, productId, size;
    private Integer amount, fee;

}
