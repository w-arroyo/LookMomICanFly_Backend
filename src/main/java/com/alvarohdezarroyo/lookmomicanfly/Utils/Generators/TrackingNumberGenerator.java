package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TrackingNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    private static int generateRandomNumberBetweenARange(int min, int maxNotIncluded){
        return random.nextInt(min,maxNotIncluded);
    }

    private static int generateRandomSingleDigitNumber(){
        return generateRandomNumberBetweenARange(0,10);
    }

    private static char generateRandomLetter(){
        return (char) ('A'+ generateRandomNumberBetweenARange(0,26));
    }

    private static void addCertainAmountOfLettersToStringBuilder(StringBuilder string, int range){
        for (int position=0; position<range; position++) {
            string.append(generateRandomLetter());
        }
    }

    private static void addCertainAmountOfDigitsToStringBuilder(StringBuilder stringBuilder, int range){
        for (int position=0;  position<range; position++) {
            stringBuilder.append(generateRandomSingleDigitNumber());
        }
    }

    public static String generateTrackingNumber(){
        StringBuilder trackingNumber=new StringBuilder();
        trackingNumber.append(generateRandomSingleDigitNumber());
        trackingNumber.append(generateRandomLetter());
        addCertainAmountOfDigitsToStringBuilder(trackingNumber,3);
        addCertainAmountOfLettersToStringBuilder(trackingNumber,3);
        addCertainAmountOfDigitsToStringBuilder(trackingNumber,8);
        return trackingNumber.toString();
    }

}
