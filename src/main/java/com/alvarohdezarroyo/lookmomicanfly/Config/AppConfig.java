package com.alvarohdezarroyo.lookmomicanfly.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
public class AppConfig {

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

    public static String getEmail(){
        return emailAddress;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
