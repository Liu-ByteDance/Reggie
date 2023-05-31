package com.itliu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliu.reggie.dto.DishDto;
import com.itliu.reggie.entity.Dish;

/**
 * @create 2023-05-21-9:18
 */
public interface DishService extends IService<Dish> {
    public void savewithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
