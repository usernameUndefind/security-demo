package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class OrderApi {

    public static void main(String[] args) {
        SpringApplication.run(OrderApi.class, args);
    }
}
