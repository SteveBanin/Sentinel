package com.sbporg.slosentinel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.sbporg.slosentinel.api")
public class SentinelApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(SentinelApiApplication.class, args);
  }
}