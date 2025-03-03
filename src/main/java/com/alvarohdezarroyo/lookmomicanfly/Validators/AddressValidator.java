package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressValidator {

    public static void checkIfFieldsAreEmpty(AddressDTO addressDTO){
        List<String> emptyFields=new ArrayList<>();
        if(addressDTO.getUserId().isBlank()){
            emptyFields.add("userId");
        }
        if(addressDTO.getFullName().isBlank()){
            emptyFields.add("fullName");
        }
        if(addressDTO.getStreet().isBlank()){
            emptyFields.add("street");
        }
        if(addressDTO.getCity().isBlank()){
            emptyFields.add("city");
        }
        if(addressDTO.getZipCode().isBlank()){
            emptyFields.add("zipCode");
        }
        if(addressDTO.getCountry().isBlank()){
            emptyFields.add("country");
        }
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

}
