package com.zhuze.springboot.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.zhuze.springboot.service.IUserService;
import com.zhuze.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-04-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //微信小程序登录
    @GetMapping("/login")
    public Result login(@RequestParam(defaultValue = "") String userUsername,
                        @RequestParam(defaultValue = "") String userPassword) {
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_username", userUsername);
        queryWrapper.eq("user_password", userPassword);
        User one;
        one = userService.getOne(queryWrapper);
        if (one!=null) {
            return Result.success(userService.getOne(queryWrapper));
        } else {
            return Result.error();
        }
    }

    //微信小程序个人信息
    @GetMapping("/userInfo")
    public Result login(@RequestParam String userUsername) {
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_username", userUsername);
        return Result.success(userService.getOne(queryWrapper));
    }


    //新增或修改
    @PostMapping
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String userUsername,
                               @RequestParam(defaultValue = "") String nickname,
                               @RequestParam(required = true,defaultValue= "" ) Boolean userGender,
                               @RequestParam(defaultValue = "") String userEmail,
                               @RequestParam(defaultValue = "") String userPhone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("user_id");
        queryWrapper.like(!Strings.isEmpty(userUsername),"user_username",userUsername);
        queryWrapper.like(!Strings.isEmpty(nickname),"nickname",nickname);
        if(userGender != null) {
            queryWrapper.eq("user_gender",userGender);
        }
        queryWrapper.like(!Strings.isEmpty(userEmail),"user_email",userEmail);
        queryWrapper.like(!Strings.isEmpty(userPhone),"user_phone",userPhone);
        return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }


    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<User> list = userService.list();
        //通过工具类创建writer写出到磁盘路径

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名 ->（已用@Alias("别名")代替）
//        writer.addHeaderAlias("userUsername", "用户名");
//        writer.addHeaderAlias("userPassword", "密码");
//        writer.addHeaderAlias("nickname", "昵称");
//        writer.addHeaderAlias("userGender", "性别");
//        writer.addHeaderAlias("userEmail", "邮箱");
//        writer.addHeaderAlias("userPhone", "电话");
//        writer.addHeaderAlias("userBirthTime", "出生日期");
//        writer.addHeaderAlias("userCreateTime", "创建时间");

        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    //导入
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return Result.success(true);
    }

    //查询数量
    @GetMapping("/count")
    public Result findCount() {
        return Result.success(userService.count());
    }
}

