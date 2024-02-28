package org.sparta.cw.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
