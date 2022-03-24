package org.fungover.webapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users").hasAuthority("CREATE_USER")
                .antMatchers(HttpMethod.GET,"/products").permitAll()
                .antMatchers(HttpMethod.POST,"/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/products/**").hasAuthority("DELETE")
                .anyRequest().authenticated()
                .and().build();
    }
}
