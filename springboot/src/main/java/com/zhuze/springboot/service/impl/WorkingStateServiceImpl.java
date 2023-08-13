package com.zhuze.springboot.service.impl;

import com.zhuze.springboot.entity.WorkingState;
import com.zhuze.springboot.mapper.WorkingStateMapper;
import com.zhuze.springboot.service.IWorkingStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-08
 */
@Service
public class WorkingStateServiceImpl extends ServiceImpl<WorkingStateMapper, WorkingState> implements IWorkingStateService {

}
