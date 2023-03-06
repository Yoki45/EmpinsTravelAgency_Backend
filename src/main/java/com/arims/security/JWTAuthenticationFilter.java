package com.arims.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.arims.dto.LoginDto;
import com.arims.dto.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            LoginDto creds = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);
            String username = creds.getUsername();
            String password = creds.getPassword();

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
        } catch (IOException e) {
            log.warn("Authentication failed for hostname" + req.getLocalName() + " Ip address: " + req.getLocalAddr());

        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        log.warn("Authentication Successful User " + ((User) auth.getPrincipal()).getUsername()
                + " Ip address: " + req.getRemoteAddr());
        String email = ((User) auth.getPrincipal()).getUsername();
        Token token = new Token();

        token.setToken(jwtService.generateToken(email));

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(token));
        out.flush();

    }
}
