package com.alvarohdezarroyo.lookmomicanfly.DTO;

import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String id, name, email, password;
    private UserType userType;
}
