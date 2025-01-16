package com.fernandocanabarro.oak_desafio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // @Bean
    // @Order(1)
    // public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
    //     http.securityMatcher(PathRequest.toH2Console())
    //         .csrf(csrf -> csrf.disable())
    //         .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
    //     return http.build();
    // }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers("/auth/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll())
            .formLogin(form -> form.loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login")
                .permitAll())
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
