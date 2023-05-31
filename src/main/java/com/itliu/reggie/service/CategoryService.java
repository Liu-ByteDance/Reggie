package com.itliu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliu.reggie.entity.Category;

/**
 * @create 2023-05-19-20:49
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
