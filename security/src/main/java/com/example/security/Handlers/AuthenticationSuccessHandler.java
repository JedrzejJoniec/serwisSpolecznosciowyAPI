package com.example.security.Handlers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${jwt.expirationTime}")
    private final long expirationTime;


    private final String secret;

    public AuthenticationSuccessHandler(@Value("${jwt.expirationTime}") long expirationTime, @Value("${jwt.secret}") String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        File privateKeyFile = new File("/home/key.priv");

        if (!privateKeyFile.exists()) {
            privateKeyFile = new File("C:\\Users\\jedrz\\IdeaProjects\\projekt\\security\\key.priv");
            if (!privateKeyFile.exists()) {
                privateKeyFile = new File("C:\\home\\site\\key.priv");
            }
        }
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

        KeyFactory privateKeyFactory = null;
        try {
            privateKeyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        RSAPrivateKey privateKey = null;
        try {
            privateKey = (RSAPrivateKey) privateKeyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        UserDetails principal = (UserDetails) authentication.getPrincipal(); // 1
        String token = JWT.create() // 2
                .withSubject(principal.getUsername()) // 3
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 4
                .sign(Algorithm.RSA256(privateKey)); // 5
        response.getWriter().write("Bearer " + token);
        response.addHeader("Authorization", "Bearer " + token);
    }
}