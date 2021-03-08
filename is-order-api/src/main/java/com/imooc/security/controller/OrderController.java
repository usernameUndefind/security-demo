package com.imooc.security.controller;

import com.imooc.security.order.OrderInfo;
import com.imooc.security.order.PriceInfo;
import com.imooc.security.order.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @AuthenticationPrincipal User user) {

        log.info("user is {}", user);
//        PriceInfo forObject = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(), PriceInfo.class);
//
//        log.info("price is {}", forObject.getPrice());
        return info;
    }


    @GetMapping("{id}")
    public void get(@PathVariable Integer id) {
        log.info("id = {}", id);
    }
}
