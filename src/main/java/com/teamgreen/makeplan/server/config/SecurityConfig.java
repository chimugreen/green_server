package com.teamgreen.makeplan.server.config;

import com.teamgreen.makeplan.server.auth.JwtAuthenticationEntryPoint;
import com.teamgreen.makeplan.server.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JwtAuthenticationEntryPoint entryPoint;

    /**
     * Password encoder password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Spring Security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/signup", "/auth/signin")
                                               .permitAll()
                                               .requestMatchers(                    "/v3/api-docs/**",
                                                                                    "/swagger-ui/**",
                                                                                    "/swagger-ui.html",
                                                                                    "/swagger-resources/**",
                                                                                    "/webjars/**")
                                               .permitAll()

            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
