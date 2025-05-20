package com.withcare;

import com.withcare.util.JwtToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WithCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithCareApplication.class, args);

        if (JwtToken.JwtUtils.getPri_key() == null) {
            JwtToken.JwtUtils.setPri_key();
        }
    }

}
