package com.example.chattproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChattProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChattProjectApplication.class, args);
    }

}
