package com.pulse.footballpulse;

import com.pulse.footballpulse.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FootballPulseApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootballPulseApplication.class, args);
    }
}
