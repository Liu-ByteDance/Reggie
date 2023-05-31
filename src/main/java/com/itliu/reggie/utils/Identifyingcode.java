package com.itliu.reggie.utils;

import cn.hutool.core.util.RandomUtil;

import javax.servlet.http.HttpSession;

/**
 * @create 2023-05-27-19:53
 */
public class Identifyingcode {
    //按照传进来的参数大小，生成相应位数验证码
    public static String idetifyingcode(int num){
        String code = RandomUtil.randomNumbers(num);

        return code;
    }
}
