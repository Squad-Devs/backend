package com.shdwraze.metro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.shdwraze")
@EnableCaching
public class MetroBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBackendApplication.class, args);
    }

}
