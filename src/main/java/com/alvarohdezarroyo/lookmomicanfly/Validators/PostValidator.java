package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Requests.PostRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostValidator {

    public static void checkIfPostFieldsAreEmpty(PostRequest postRequest, String specialField, String specialFieldName){
        final List<String> emptyFields=new ArrayList<>();
        if(postRequest.getAddressId()==null || postRequest.getAddressId().trim().isEmpty())
            emptyFields.add("address");
        if(postRequest.getUserId()==null || postRequest.getUserId().trim().isEmpty())
            emptyFields.add("user");
        if(postRequest.getSize()==null || postRequest.getSize().trim().isEmpty())
            emptyFields.add("size");
        try {
            GlobalValidator.checkIfANumberFieldIsValid(postRequest.getAmount());
        }
        catch (IllegalArgumentException e){
            emptyFields.add("amount");
        }
        if(postRequest.getProductId()==null || postRequest.getProductId().trim().isEmpty())
            emptyFields.add("product");
        if(specialField==null || specialField.trim().isEmpty())
            emptyFields.add(specialFieldName);
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

}
