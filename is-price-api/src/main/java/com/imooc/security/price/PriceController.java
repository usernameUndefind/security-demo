package com.imooc.security.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/prices")
@Slf4j
public class PriceController {


    @GetMapping("/{id}")
    public PriceInfo getPrice(@PathVariable("id") Long id) {
        log.info("orderId is {}", id);
        PriceInfo info = new PriceInfo();
        info.setId(id);
        info.setPrice(new BigDecimal(100));
        return info;
    }

}
