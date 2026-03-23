package com.example.gharseva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GharSevaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GharSevaApplication.class, args);
    }

}
