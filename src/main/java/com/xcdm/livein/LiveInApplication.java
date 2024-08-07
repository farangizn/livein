package com.xcdm.livein;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LiveInApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveInApplication.class, args);
    }
}
