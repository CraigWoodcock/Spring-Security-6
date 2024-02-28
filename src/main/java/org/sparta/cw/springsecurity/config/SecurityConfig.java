package org.sparta.cw.springsecurity.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount","/myCards", "/myLoans", "/myBalance").authenticated()
                .requestMatchers("/notices", "/contact", "/welcome").permitAll());
                http.formLogin(Customizer.withDefaults());
                http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


}
