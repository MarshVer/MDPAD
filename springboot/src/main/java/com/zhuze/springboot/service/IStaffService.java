package com.zhuze.springboot.service;

import com.zhuze.springboot.controller.dto.StaffDTO;
import com.zhuze.springboot.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-30
 */
public interface IStaffService extends IService<Staff> {

    StaffDTO login(StaffDTO staffDTO);
}
