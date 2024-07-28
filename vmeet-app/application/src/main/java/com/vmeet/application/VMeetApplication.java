package com.vmeet.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vmeet")
public class VMeetApplication {
  public static void main(String[] args) {

    String redisHost = System.getenv("REDIS_HOST");
    String mongoHost = System.getenv("MONGO_HOST");

    System.out.printf("Ahmed+ redis: %s, mongo: %s%n", redisHost, mongoHost);

    SpringApplication.run(VMeetApplication.class, args);
  }
}
