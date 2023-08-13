package com.zhuze.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuze.springboot.entity.Menu;
import com.zhuze.springboot.mapper.MenuMapper;
import com.zhuze.springboot.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-05-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("menu_id");
        if (StrUtil.isNotBlank(menuName)) {
            queryWrapper.like(!Strings.isEmpty(menuName),"menu_name",menuName);
        }
        //查询所有数据
        List<Menu> list = list(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            menu.setChildren(list.stream().filter(m -> menu.getMenuId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
