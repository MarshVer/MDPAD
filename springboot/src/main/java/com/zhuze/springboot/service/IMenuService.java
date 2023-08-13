package com.zhuze.springboot.service;

import com.zhuze.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-07
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String menuName);

}
