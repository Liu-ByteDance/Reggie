package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.entity.Employee;
import com.itliu.reggie.mapper.EmployeeMapper;
import com.itliu.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-17-16:07
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService  {
}
