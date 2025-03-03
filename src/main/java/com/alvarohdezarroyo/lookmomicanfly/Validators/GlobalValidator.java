package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;

public class GlobalValidator {

    public static void checkFraudulentRequest(String requestUserId, String repositoryId){
        if(!requestUserId.equalsIgnoreCase(repositoryId))
            throw new FraudulentRequestException("User sending the request does not have permission.");
    }

    public static void checkIfTwoFieldsAreEmpty(String one, String two){
        if (one==null || one.isBlank() || two==null || two.isBlank())
            throw new EmptyFieldsException("Request is empty.");
    }

}
