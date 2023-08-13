package com.zhuze.springboot.exception;

import com.zhuze.springboot.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: ExceptionHandler
 * Package: com.zhuze.springboot.exception
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/4/30 23:37
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }
}
