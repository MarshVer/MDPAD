package com.zhuze.springboot.service.impl;

import com.zhuze.springboot.entity.Drone;
import com.zhuze.springboot.mapper.DroneMapper;
import com.zhuze.springboot.service.IDroneService;
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
public class DroneServiceImpl extends ServiceImpl<DroneMapper, Drone> implements IDroneService {

}
