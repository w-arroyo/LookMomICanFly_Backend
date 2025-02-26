package com.alvarohdezarroyo.lookmomicanfly.Exceptions;

public class EmailAlreadyInUse extends RuntimeException {
    public EmailAlreadyInUse(String message) {
        super(message);
    }
}
