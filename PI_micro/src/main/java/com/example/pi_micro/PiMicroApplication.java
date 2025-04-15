package com.example.pi_micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PiMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiMicroApplication.class, args);
    }

}
