package com.itliu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliu.reggie.entity.Orders;

/**
 * @create 2023-05-28-15:32
 */
public interface OrdersService extends IService<Orders> {
    //用户下单
    public void submit(Orders orders);
}
