package com.zhuze.springboot.service.impl;

import com.zhuze.springboot.entity.OrderState;
import com.zhuze.springboot.mapper.OrderStateMapper;
import com.zhuze.springboot.service.IOrderStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-09
 */
@Service
public class OrderStateServiceImpl extends ServiceImpl<OrderStateMapper, OrderState> implements IOrderStateService {

}
