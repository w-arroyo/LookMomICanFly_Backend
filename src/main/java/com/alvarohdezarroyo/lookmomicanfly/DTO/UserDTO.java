package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer id, userTypeId;
    private String name, email, password;
}
