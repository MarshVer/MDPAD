package com.zhuze.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuze.springboot.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: RoleMenuMapper
 * Package: com.zhuze.springboot.mapper
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/5/7 15:56
 * @Version 1.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select menu_id from role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
