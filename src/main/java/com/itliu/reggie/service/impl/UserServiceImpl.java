package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.entity.User;
import com.itliu.reggie.mapper.UserMapper;
import com.itliu.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-27-17:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
