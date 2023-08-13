package com.zhuze.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Staff;
import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IRoleService;
import com.zhuze.springboot.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-07
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String roleName,
                           @RequestParam(defaultValue = "0") Integer flag) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("flag");
        queryWrapper.like(!Strings.isEmpty(roleName),"role_name",roleName);
        if(flag != 0) {
            queryWrapper.eq("flag",flag);
        }
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    //绑定角色和菜单的关系
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,
                       @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    //绑定角色和菜单的关系
    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }


//    //导出
//    @GetMapping("/export")
//    public void export(HttpServletResponse response) throws Exception {
//        //从数据库查询出所有的数据
//        List<Role> list = roleService.list();
//        //通过工具类创建writer写出到磁盘路径
//
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
//        writer.write(list,true);
//
//        //设置浏览器相应格式
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        String fileName = URLEncoder.encode("信息", StandardCharsets.UTF_8);
//        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
//
//        ServletOutputStream out = response.getOutputStream();
//        writer.flush(out, true);
//        out.close();
//        writer.close();
//    }
//
//    //导入
//    @PostMapping("/import")
//    public Result imp(MultipartFile file) throws Exception {
//        InputStream inputStream = file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        List<Role> list = reader.readAll(Role.class);
//        roleService.saveBatch(list);
//        return Result.success(true);
//    }
}

