package com.alvarohdezarroyo.lookmomicanfly.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disables CSRF (useful for developing APIs REST)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // enables CORS
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/users/register").permitAll(); // allows access without authentication
                    auth.requestMatchers("/api/users/login").permitAll();
                    auth.requestMatchers("/api/addresses/save").permitAll();
                    //auth.requestMatchers(HttpMethod.GET, "/api/users/deactivate/{email}").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/users/addresses/{userId}").permitAll();
                    auth.anyRequest().authenticated(); // any other route requires authorization
                });
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // allows requests from any origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // allowed methods
        configuration.setAllowedHeaders(List.of("*")); // allows headers of any type
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // applies to all it's routes
        return source;
    }
}
