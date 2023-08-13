package com.zhuze.springboot.exception;

import lombok.Getter;

/**
 * ClassName: ServiceException
 * Package: com.zhuze.springboot.exception
 * Description:自定义异常
 *
 * @Author 朱泽
 * @Create 2023/4/30 23:40
 * @Version 1.0
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
