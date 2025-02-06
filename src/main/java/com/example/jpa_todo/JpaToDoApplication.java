package com.example.jpa_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaToDoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaToDoApplication.class, args);
    }

}
