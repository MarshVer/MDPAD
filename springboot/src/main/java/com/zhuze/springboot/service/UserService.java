package com.zhuze.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuze.springboot.entity.User;
import com.zhuze.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 * Package: com.zhuze.springboot.service
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/4/15 20:47
 * @Version 1.0
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User>  {
    public boolean saveUser(User user) {
//        id(user.getId() == null) {
//            return save(user);
//        } else {
//            updateById(user);
//        }

        return saveOrUpdate(user);
    }
//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user) {
//        if(user.getId() == null) { //user没有id，新增
//            return userMapper.insert(user);
//        } else {    //user有id，更新
//            return userMapper.update(user);
//        }
//    }
}
