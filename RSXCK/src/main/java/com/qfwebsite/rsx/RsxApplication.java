package com.qfwebsite.rsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RsxApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsxApplication.class, args);
    }

}
