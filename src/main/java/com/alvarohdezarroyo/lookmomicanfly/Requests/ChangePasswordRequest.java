package com.alvarohdezarroyo.lookmomicanfly.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePasswordRequest {
    private String id, oldPassword, newPassword;
}
