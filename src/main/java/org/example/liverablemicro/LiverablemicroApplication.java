package org.example.liverablemicro;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableDiscoveryClient

public class LiverablemicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiverablemicroApplication.class, args);
    }

}
