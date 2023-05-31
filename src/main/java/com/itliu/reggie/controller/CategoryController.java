package com.itliu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliu.reggie.common.R;
import com.itliu.reggie.entity.Category;
import com.itliu.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @create 2023-05-19-20:52
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> ldw = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        ldw.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,ldw);

        return R.success(pageInfo);
    }
    //删除菜品
    @DeleteMapping
    public R<String> deleteById(@RequestParam("ids") Long id){
//        log.info("删除id: {}",id);
        categoryService.remove(id);
        return R.success("删除成功");
    }
    //更新菜品
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("category: {}",category);
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }
    //根据条件，查询分类
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }
}
