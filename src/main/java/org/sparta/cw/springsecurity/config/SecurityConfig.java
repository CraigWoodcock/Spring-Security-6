package org.sparta.cw.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // Custom security filter allows authenticated traffic to secured endpoints and allows all traffic to
    // unsecured endpoints.
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount","/myCards", "/myLoans", "/myBalance").authenticated()
                .requestMatchers("/notices", "/contact", "/welcome").permitAll());
                http.formLogin(Customizer.withDefaults());
                http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    // invoke passwordEncoder without encoding password.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    // create users without defaultPassowrdEncoder
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails admin = User.withUsername("admin")
//                .password("password")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("password123")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    // Create users with password encoder.
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password123")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    // Deny all traffic to all endpoints
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .anyRequest().denyAll());
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

    // permits all traffic to all endpoints
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .anyRequest().permitAll());
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
//    }


}
