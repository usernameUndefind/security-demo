package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableZuulProxy
@EnableJpaAuditing
@ComponentScan(basePackages = "com.imooc.security.repository")
public class GateServer {

    public static void main(String[] args) {
        SpringApplication.run(GateServer.class, args);
    }

}
