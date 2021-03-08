package com.imooc.security.controller;

import com.imooc.security.order.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader String username) {

        log.info("user is {}", username);
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
