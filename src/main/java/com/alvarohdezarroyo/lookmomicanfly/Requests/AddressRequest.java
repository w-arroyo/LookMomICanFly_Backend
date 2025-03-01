package com.alvarohdezarroyo.lookmomicanfly.Requests;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private AddressDTO address;
    private String email;
}
