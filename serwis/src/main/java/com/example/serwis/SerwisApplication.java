package com.example.serwis;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SerwisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerwisApplication.class, args);

	}


}
