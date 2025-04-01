package com.alvarohdezarroyo.lookmomicanfly.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;

@Configuration
@Getter
public class AppConfig {

    private final static String SHIPPING_LABELS_PATH = System.getProperty("user.dir")+ File.separator+"Shipping Labels"+File.separator;
    private static String aesKey, emailAddress;

    @Value("${app.aesKey}")
    public void setAesKey(String value) {
        aesKey= value;
    }

    @Value("${app.emailAddress}")
    public void setEmailAddress(String value){
        emailAddress=value;
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

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
