package com.zhuze.springboot.mapper;

import com.zhuze.springboot.entity.Number;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-05-04
 */
@Mapper
public interface NumberMapper extends BaseMapper<Number> {

}
