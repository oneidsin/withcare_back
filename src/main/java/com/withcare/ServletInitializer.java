package com.withcare;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.withcare.util.JwtToken;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("키 생성");

        if (JwtToken.JwtUtils.getPri_key() == null) {
            JwtToken.JwtUtils.setPri_key();
        }

        return application.sources(WithCareApplication.class);
    }

}
