package com.zhuze.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Result
 * Package: com.zhuze.springboot.commoin
 * Description:接口统一返回包装类
 *
 * @Author 朱泽
 * @Create 2023/4/30 23:12
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

}
