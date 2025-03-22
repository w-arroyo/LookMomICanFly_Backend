package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

@Component
public class ReferenceGenerator {

    public static String generateRandomReference(){
        final StringBuilder reference=new StringBuilder();
        int character=0;
        while(character<36){
            if((character>4 && character<9) ||(character>19&&character<23) || (character>30 && character<33))
                reference.append(RandomGenerator.generateRandomSingleDigitNumber());
            else addRandomLetter(reference);
            character++;
        }
        return reference.toString();
    }

    private static void addRandomLetter(StringBuilder string){
        if(RandomGenerator.generateRandomSingleDigitNumber()%2==0)
            string.append(RandomGenerator.generateRandomUpperCaseLetter());
        else string.append(RandomGenerator.generateRandomLowerCaseLetter());
    }

}
