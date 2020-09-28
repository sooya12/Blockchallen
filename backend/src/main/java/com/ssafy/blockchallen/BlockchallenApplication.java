package com.ssafy.blockchallen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlockchallenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchallenApplication.class, args);
    }

}
