package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * ClassName: RoleMenu
 * Package: com.zhuze.springboot.entity
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/5/7 15:54
 * @Version 1.0
 */
@TableName("role_menu")
@Data
public class RoleMenu {

    private Integer roleId;
    private Integer menuId;
}
