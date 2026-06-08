package com.pos.dashboardmvc.shared.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) {

        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/",
                            "/auth/login",
                            "/auth/register",
                            "/error-page/**",
                            "/css/**",
                            "/js/**",
                            "/img/**",
                            "/plugins/**",
                            "/bootstrap/**"
                    ).permitAll()

                            .requestMatchers("/admin/**")
                            .hasAnyRole("ADMIN", "MANAGER")

                            .anyRequest()
                            .authenticated()
            )
            .csrf(csrf -> csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .formLogin(form -> form
                    .loginPage("/auth/login")
                    .successHandler(successHandler)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )
            .exceptionHandling(ex -> ex
                    .accessDeniedPage("/error-page/403")
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(
                    userDetailsService
            );

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}