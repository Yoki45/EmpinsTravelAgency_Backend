package com.arims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.arims.enums.Role;

import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurity {

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  public AuthenticationManager authenticationManager()
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService,
      UserDetailsService userDetailsService) throws Exception {
    JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(authenticationManager(), jwtService);
    authFilter.setFilterProcessesUrl("/login");
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.toString())
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(authFilter)
        .addFilterAfter(new JWTAuthorizationFilter(userDetailsService, jwtService), JWTAuthenticationFilter.class);

    return http.build();
  }
}
