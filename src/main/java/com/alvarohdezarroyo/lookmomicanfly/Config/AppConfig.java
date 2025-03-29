package com.alvarohdezarroyo.lookmomicanfly.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
public class AppConfig {

    private static String aesKey;

    @Value("${app.aesKey}")
    public void setAesKey(String value) {
        aesKey= value;
    }

    public static String getKey(){
        return aesKey;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
