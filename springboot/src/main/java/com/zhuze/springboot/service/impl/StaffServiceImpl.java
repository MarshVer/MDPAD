package com.zhuze.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuze.springboot.common.Constants;
import com.zhuze.springboot.controller.dto.StaffDTO;
import com.zhuze.springboot.entity.Menu;
import com.zhuze.springboot.entity.RoleMenu;
import com.zhuze.springboot.entity.Staff;
import com.zhuze.springboot.exception.ServiceException;
import com.zhuze.springboot.mapper.RoleMapper;
import com.zhuze.springboot.mapper.RoleMenuMapper;
import com.zhuze.springboot.mapper.StaffMapper;
import com.zhuze.springboot.service.IMenuService;
import com.zhuze.springboot.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuze.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Literal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-30
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {
    private static final Log LOG = Log.get();

    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public StaffDTO login(StaffDTO staffDTO) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_username", staffDTO.getStaffUsername());
        queryWrapper.eq("staff_password", staffDTO.getStaffPassword());
        Staff one;
        try {
            one = getOne(queryWrapper); //从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if(one != null) {
            BeanUtil.copyProperties(one, staffDTO, true);
            String token = TokenUtils.genToken(one.getStaffId().toString(), one.getStaffPassword());
            staffDTO.setToken(token);

            String role = String.valueOf(one.getPosition());
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            staffDTO.setMenus(roleMenus);
            return staffDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }


    //获取当前角色的菜单列表
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //当前角色的所有菜单id的集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getMenuId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf()移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getMenuId()));
        }
        return roleMenus;
    }
}