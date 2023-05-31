package com.itliu.reggie.common;

/**
 * @create 2023-05-21-9:44
 */
//自定义业务异常
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
