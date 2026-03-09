package com.sbporg.slosentinel.worker.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sbporg.slosentinel.worker")
public class SloSentinelWorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SloSentinelWorkerApplication.class, args);
    }
}