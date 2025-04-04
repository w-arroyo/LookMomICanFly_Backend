package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    public static String generateToken(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ AppConfig.getTokenDuration()))
                .signWith(AppConfig.getTokenSigningSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
