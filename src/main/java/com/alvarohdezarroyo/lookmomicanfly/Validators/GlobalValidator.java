package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;

public class GlobalValidator {

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

    public static void checkIfANumberFieldIsValid(int field){
        try{
            Integer.parseInt(field+"");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number field invalid.");
        }
    }

}
