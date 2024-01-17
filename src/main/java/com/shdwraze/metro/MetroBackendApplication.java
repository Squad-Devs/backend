package com.shdwraze.metro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shdwraze")
public class MetroBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBackendApplication.class, args);
    }

}
