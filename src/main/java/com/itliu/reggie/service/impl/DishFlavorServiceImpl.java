package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.dto.DishDto;
import com.itliu.reggie.entity.DishFlavor;
import com.itliu.reggie.mapper.DishFlavorMapper;
import com.itliu.reggie.service.DishFlavorService;
import com.itliu.reggie.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-21-16:01
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService{
}
