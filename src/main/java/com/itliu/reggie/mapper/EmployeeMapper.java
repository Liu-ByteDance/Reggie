package com.itliu.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliu.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2023-05-17-16:02
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
