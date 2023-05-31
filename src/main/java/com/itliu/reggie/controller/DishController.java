package com.itliu.reggie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliu.reggie.common.R;
import com.itliu.reggie.dto.DishDto;
import com.itliu.reggie.dto.SetmealDto;
import com.itliu.reggie.entity.Category;
import com.itliu.reggie.entity.Dish;
import com.itliu.reggie.entity.DishFlavor;
import com.itliu.reggie.service.CategoryService;
import com.itliu.reggie.service.DishFlavorService;
import com.itliu.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @create 2023-05-21-16:03
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody  DishDto dishDto){
        log.info("dishdto:{}",dishDto);
        dishService.savewithFlavor(dishDto);
        //清理所有菜品的缓存数据
        Set<String> keys = stringRedisTemplate.keys("dish_*");
        stringRedisTemplate.delete(keys);
        return R.success("新增菜品成功");
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize, String name){
        //构建分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询,并将查询到的结果填充到pageInfo里面去
        dishService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = new ArrayList<>();
        for (Dish record : records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(record,dishDto);
            //根据id查询分类对象
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            list.add(dishDto);
        }

        dishDtoPage.setRecords(list);
        //返回
        return R.success(dishDtoPage);
    }

    //根据id查询对应的菜品信息，和口味
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品信息
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        //清理所有菜品的缓存数据
        Set<String> keys = stringRedisTemplate.keys("dish_*");
        stringRedisTemplate.delete(keys);

        return R.success("修改菜品成功");
    }

    //根据条件查询对应的菜品数据
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        //构造查询条件
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
//        //添加条件，查询状态为1的，1代表正在售卖
//        queryWrapper.eq(Dish::getStatus,1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        //进行查询，并返回集合
//        List<Dish> list = dishService.list(queryWrapper);
//
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = null;

        //先从redis中获取缓存数据
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        //先从redis中获取缓存数据
        String data = stringRedisTemplate.opsForValue().get(key);
        if(data != null){
            //如果存在，直接返回，无需查询数据库
            dishDtoList= JSONArray.parseArray(data,DishDto.class);
            return R.success(dishDtoList);
        }
        //不存在则直接查询数据库
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1的，1代表正在售卖
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        //进行查询，并返回集合
        List<Dish> list = dishService.list(queryWrapper);
        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //对象拷贝
            BeanUtils.copyProperties(item,dishDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(categoryId != null){
                //分类名称
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            //当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        //如果不存在，需要查询数据库，将查询到的菜品数据缓存到Redis
        String jsonStr = JSON.toJSONString(dishDtoList);
        stringRedisTemplate.opsForValue().set(key,jsonStr,60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }
}
