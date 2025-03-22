package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static int generateRandomNumberBetweenARange(int min, int maxNotIncluded){
        return random.nextInt(min,maxNotIncluded);
    }

    public static int generateRandomSingleDigitNumber(){
        return generateRandomNumberBetweenARange(0,10);
    }

    public static char generateRandomLetter(){
        return (char) ('A'+ generateRandomNumberBetweenARange(0,26));
    }

}
