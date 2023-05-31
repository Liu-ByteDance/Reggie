package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.entity.OrderDetail;
import com.itliu.reggie.mapper.OrderDetailMapper;
import com.itliu.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-28-15:36
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
