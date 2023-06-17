package com.witcher.ttrpgapi.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

/*   @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }*/
/*

@Bean
AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new CustomAuthenticationSuccessHandler();
}

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Autowired
    private MyAuthenticationProvider firstAuthProvider;



    public AuthenticationManager authManager() throws Exception {
        //AuthenticationManager authManager = new ProviderManager(new MyFirstCustomAuthenticationProvider());
        AuthenticationManager authManager = new ProviderManager(firstAuthProvider);
        return authManager;
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

   /* http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/login-process").permitAll()
            .requestMatchers("/fail").permitAll()
            .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginProcessingUrl("/login-process")
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
                .permitAll()
           ;
     //       .addFilter(new BasicAuthenticationFilter(authManager()));
        return http.build();*/

/*        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers("/signin").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();*/


   //      MŰKÖDIK
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests(auth -> {
                   // auth.requestMatchers("/stories").permitAll();
                    auth.requestMatchers("/").permitAll();
                })

               .formLogin()
                .loginPage("/login")
                .permitAll()
               .and()
               .httpBasic(withDefaults());
        return http.build();

    }

/*    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/




}
