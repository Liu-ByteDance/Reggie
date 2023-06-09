package com.itliu.reggie.controller;

import com.itliu.reggie.common.R;
import com.itliu.reggie.entity.Orders;
import com.itliu.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @create 2023-05-28-15:34
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    //用户下单
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据: {}",orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }
}
