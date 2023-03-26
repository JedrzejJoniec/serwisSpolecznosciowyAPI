package com.example.broker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("public-key")
    String key;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = null; // 1
        try {
            authentication = getAuthentication(request);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication); // 2
        filterChain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {



        File publicKeyFile = new File("/home/key.pub");

        if (!publicKeyFile.exists()) {
            publicKeyFile = new File("C:\\Users\\jedrz\\IdeaProjects\\projekt\\broker\\src\\main\\resources\\static\\key.pub");
            if (!publicKeyFile.exists()) {
                publicKeyFile = new File("C:\\home\\site\\key.priv");
            }
        }
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        RSAPublicKey publicKey = (RSAPublicKey) publicKeyFactory.generatePublic(publicKeySpec);

        String token = request.getHeader(TOKEN_HEADER); // 3
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String userName = JWT.require(Algorithm.RSA256(publicKey)) // 4
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, "")) // 5
                    .getSubject(); // 6
            if (userName != null) {
                return new UsernamePasswordAuthenticationToken(userName, null, null); // 8
            }
        }
        return null;
    }
}