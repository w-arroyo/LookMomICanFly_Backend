package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;

public class GlobalValidator {

    public static void checkFraudulentRequest(String requestUserId, String authenticatedUserId){
        if(!requestUserId.equalsIgnoreCase(authenticatedUserId))
            throw new FraudulentRequestException("User sending the request does not have permission.");
    }

    public static void checkIfAFieldIsEmpty(String field){
        if(field==null || field.isBlank())
            throw new EmptyFieldsException("Request is empty.");
    }

    public static void checkIfTwoFieldsAreEmpty(String one, String two){
        if (one==null || one.isBlank() || two==null || two.isBlank())
            throw new EmptyFieldsException("Empty fields are not allowed.");
    }

    public static void checkIfRequestBodyIsEmpty(Object object){
        if(object==null)
            throw new IllegalArgumentException("Request is empty.");
    }

}
