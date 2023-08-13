package com.zhuze.springboot.service.impl;

import com.zhuze.springboot.entity.Number;
import com.zhuze.springboot.mapper.NumberMapper;
import com.zhuze.springboot.service.INumberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-04
 */
@Service
public class NumberServiceImpl extends ServiceImpl<NumberMapper, Number> implements INumberService {

}
