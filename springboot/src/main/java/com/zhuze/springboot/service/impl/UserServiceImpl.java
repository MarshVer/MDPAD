package com.zhuze.springboot.service.impl;


import com.zhuze.springboot.entity.User;
import com.zhuze.springboot.mapper.UserMapper;
import com.zhuze.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
