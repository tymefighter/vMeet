package com.vmeet.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vmeet")
public class VMeetApplication {
  public static void main(String[] args) {
    SpringApplication.run(VMeetApplication.class, args);
  }
}
