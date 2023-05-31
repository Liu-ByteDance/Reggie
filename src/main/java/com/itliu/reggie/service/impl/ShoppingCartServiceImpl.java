package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.entity.ShoppingCart;
import com.itliu.reggie.mapper.ShoppingCartMapper;
import com.itliu.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-28-10:57
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
