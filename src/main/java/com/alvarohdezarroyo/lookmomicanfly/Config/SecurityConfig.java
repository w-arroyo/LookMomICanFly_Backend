package com.alvarohdezarroyo.lookmomicanfly.Config;

import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disables CSRF (useful for developing APIs REST)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // enables CORS
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/users/register").permitAll(); // allows access without authentication
                    auth.requestMatchers("/api/users/login").permitAll();
                    auth.requestMatchers("/api/products/get/**").permitAll();
                    auth.requestMatchers("/api/products/sneakers/get/**").permitAll();
                    //auth.anyRequest().permitAll(); this would allow any request without being authenticated
                    auth.anyRequest().authenticated(); // requires being logged in to access any request
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // handles sessions automatically
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository())) // saves session context
                .formLogin(AbstractHttpConfigurer::disable) // disables login via html form
                .logout(logout -> logout.logoutUrl("/api/users/logout").logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Successful logout");
                }));
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

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(provider);
    }

}
