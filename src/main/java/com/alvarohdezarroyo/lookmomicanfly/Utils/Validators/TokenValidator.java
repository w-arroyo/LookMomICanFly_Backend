package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenValidator {

    private static Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(AppConfig.getTokenSigningSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getTokenUserId(String token){
        return extractClaims(token).getSubject();
    }

    public static boolean checkIfTokenIsStillValid(String token){
        return extractClaims(token).getExpiration()
                .before(new Date());
    }

    public static void checkFraudulentRequest(String token, String userId){
        if(!getTokenUserId(token).equals(userId))
            throw new FraudulentRequestException("You can not access someone else's data.");
    }

}
