package com.zhuze.springboot.mapper;

import com.zhuze.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-05-07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select role_id from role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
