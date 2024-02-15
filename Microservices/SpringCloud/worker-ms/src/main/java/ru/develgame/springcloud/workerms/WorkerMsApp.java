package ru.develgame.springcloud.workerms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@EnableDiscoveryClient
@SpringBootApplication
public class WorkerMsApp {
    @Bean
    public UUID appUuid() {
        return UUID.randomUUID();
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkerMsApp.class, args);
    }
}
