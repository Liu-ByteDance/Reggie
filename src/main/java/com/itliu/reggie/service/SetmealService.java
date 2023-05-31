package com.itliu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliu.reggie.dto.SetmealDto;
import com.itliu.reggie.entity.Setmeal;

import java.util.List;

/**
 * @create 2023-05-21-9:19
 */
public interface SetmealService extends IService<Setmeal> {
    //新增套餐，同时保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐，同时需要删除套餐和菜品的关联数据
    public void removeWithDish(List<Long> ids);
}
