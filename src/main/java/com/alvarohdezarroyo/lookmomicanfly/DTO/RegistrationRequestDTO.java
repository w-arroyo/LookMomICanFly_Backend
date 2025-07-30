package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDTO extends UserDTO {

    private String ip, device;

}
