package com.itliu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliu.reggie.entity.AddressBook;
import com.itliu.reggie.mapper.AddressBookMapper;
import com.itliu.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @create 2023-05-28-8:50
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
