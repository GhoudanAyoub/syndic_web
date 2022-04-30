package com.syndicg5;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SyndicWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyndicWebApplication.class, args);
    }

}
