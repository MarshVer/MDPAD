package com.zhuze.springboot.mapper;

import com.zhuze.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-04-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
