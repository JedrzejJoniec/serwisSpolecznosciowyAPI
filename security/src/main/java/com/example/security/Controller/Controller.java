package com.example.security.Controller;


import com.example.security.Entity.UserEntity;
import com.example.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService userService;

    @GetMapping("/elo")
    public String elo() {
        System.out.println("ELOOOO=====================================");
        return "elo";
    }

    @PostMapping("/users/register/{username}")
    public ResponseEntity register(@PathVariable String username, @RequestBody String password) {
        return userService.addUser(new UserEntity(username, password, "ROLE_USER"));
    }

    @GetMapping("/users/{username}")
    public String getPassword(@PathVariable String username) {
        userService.getPassword(username);
        return userService.getPassword(username);
    }

    @GetMapping("getValidation")
    public RSAPublicKey getValidation() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        File publicKeyFile = new File("key.pub");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        RSAPublicKey publicKey = (RSAPublicKey) publicKeyFactory.generatePublic(publicKeySpec);

        return publicKey;
    }

}
