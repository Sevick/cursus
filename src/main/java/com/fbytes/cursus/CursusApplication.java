package com.fbytes.cursus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {"com.fbytes.cursus.*"})
@ConfigurationPropertiesScan("com.fbytes.cursus.config.*")
public class CursusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursusApplication.class, args);
    }
}
