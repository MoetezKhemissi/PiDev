package com.example.pi5eme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Pi5emeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pi5emeApplication.class, args);
    }

}
