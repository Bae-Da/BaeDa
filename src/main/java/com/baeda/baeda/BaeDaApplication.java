package com.baeda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan("com.baeda")
public class BaeDaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaeDaApplication.class, args);
    }

}
