package com.alvarohdezarroyo.lookmomicanfly.Config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.security.Key;

@Configuration
public class AppConfig {

    private final static String SHIPPING_LABELS_PATH = System.getProperty("user.dir")+ File.separator+"Shipping Labels"+File.separator;
    private static String aesKey, emailAddress;
    private static final Key tokenSigningKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static int tokenLength;

    @Value("${app.aesKey}")
    public void setAesKey(String value) {
        aesKey= value;
    }

    @Value("${app.emailAddress}")
    public void setEmailAddress(String value){
        emailAddress=value;
    }

    @Value("&{app.tokenLength}")
    public void setTokenLength(String value){
        tokenLength=Integer.parseInt(value);
    }

    public static String getKey(){
        return aesKey;
    }

    public static String getShippingLabelsPath(){
        return SHIPPING_LABELS_PATH;
    }

    public static String getEmail(){
        return emailAddress;
    }

    public static int getTokenDuration(){
        return tokenLength;
    }

    public static Key getTokenSigningSecretKey(){
        return tokenSigningKey;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
