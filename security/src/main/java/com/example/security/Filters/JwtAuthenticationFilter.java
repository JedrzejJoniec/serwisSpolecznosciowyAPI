package com.example.security.Filters;

import com.example.security.pojo.LoginCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
            LoginCredentials authRequest = objectMapper.readValue(sb.toString(), LoginCredentials.class);
            System.out.println(authRequest);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            );
            setDetails(request, token);
            System.out.println(token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

