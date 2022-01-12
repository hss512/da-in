package com.dain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DainApplication.class, args);
    }

}
