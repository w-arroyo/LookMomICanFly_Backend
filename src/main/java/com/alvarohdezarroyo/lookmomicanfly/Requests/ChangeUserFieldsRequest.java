package com.alvarohdezarroyo.lookmomicanfly.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserFieldsRequest {
    private String userId, newField;
}
