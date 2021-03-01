package com.imooc.security.controller;

import com.imooc.security.order.OrderInfo;
import com.imooc.security.order.PriceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info) {

        PriceInfo forObject = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(), PriceInfo.class);

        log.info("price is {}", forObject.getPrice());
        return info;
    }
}