package com.zhuze.springboot.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.zhuze.springboot.entity.Menu;
import lombok.Data;

import java.util.List;


/**
 * ClassName: StaffDTO
 * Package: com.zhuze.springboot.controller.dto
 * Description:接收前端登录请求的参数
 *
 * @Author 朱泽
 * @Create 2023/4/30 21:06
 * @Version 1.0
 */
@Data
public class StaffDTO {
    @Alias("用户名")
    private String staffUsername;
    @Alias("密码")
    private String staffPassword;
    @Alias("姓名")
    private String staffName;
    private String token;
    private String role;
    private List<Menu> menus;
}
