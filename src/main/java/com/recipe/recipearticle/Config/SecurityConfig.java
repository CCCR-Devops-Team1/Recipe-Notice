package com.recipe.recipearticle.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//  private final JwtTokenProvider jwtTokenProvider;
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(){
    return (web) -> {
      web.ignoring().requestMatchers("/**");
    };
  }
//  @Bean
//  SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//    return http
//        .csrf(c -> c.disable())
//        .formLogin( f -> f.disable())
//        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .httpBasic( h -> h.disable())
//        .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, memberRepository), UsernamePasswordAuthenticationFilter.class)
//        .addFilterBefore(new JwtExceptionFilter(), JwtTokenFilter.class)
//        .build();
//  }
  @Bean
  public AuthenticationManager authenticationmanager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
