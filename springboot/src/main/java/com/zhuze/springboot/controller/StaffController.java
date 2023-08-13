package com.zhuze.springboot.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuze.springboot.common.Constants;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.controller.dto.StaffDTO;
import com.zhuze.springboot.entity.Role;
import com.zhuze.springboot.utils.TokenUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.zhuze.springboot.service.IStaffService;
import com.zhuze.springboot.entity.Staff;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private IStaffService staffService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody StaffDTO staffDTO) {
        String staffUsername = staffDTO.getStaffUsername();
        String staffPassword = staffDTO.getStaffPassword();
        if(StrUtil.isBlank(staffUsername) || StrUtil.isBlank(staffPassword)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        StaffDTO dto = staffService.login(staffDTO);
        return Result.success(dto);
    }

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Staff staff) { return Result.success(staffService.saveOrUpdate(staff)) ;}

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(staffService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(staffService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(staffService.list());
    }

    //查询业务管理员
    @GetMapping("/yeWu")
    public Result findYeWu() {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("position",4);
        return Result.success(staffService.list(queryWrapper));
    }

    //查询数量
    @GetMapping("/count")
    public Result findCount() {
        return Result.success(staffService.count());
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(staffService.getById(id));
    }

    //个人信息数据获取
    @GetMapping("/staffUsername/{staffUsername}")
    public Result findOne(@PathVariable String staffUsername) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_username", staffUsername);
        return Result.success(staffService.getOne(queryWrapper));
    }


    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String staffName,
                                @RequestParam(defaultValue = "") String staffUsername,
                                @RequestParam(required = true,defaultValue= "" ) Boolean staffGender,
                                @RequestParam(defaultValue = "") String staffPhone,
                                @RequestParam(defaultValue = "") String idCard,
                                @RequestParam(defaultValue = "0") Integer position) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("position");
        queryWrapper.like(!Strings.isEmpty(staffName),"staff_name",staffName);
        queryWrapper.like(!Strings.isEmpty(staffUsername),"staff_username",staffUsername);
        if(staffGender != null) {
            queryWrapper.eq("staff_gender",staffGender);
        }
        queryWrapper.like(!Strings.isEmpty(staffPhone),"staff_phone",staffPhone);
        queryWrapper.like(!Strings.isEmpty(idCard),"id_card",idCard);
        if(position != 0) {
            queryWrapper.eq("position",position);
        }
        return Result.success(staffService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Staff> list = staffService.list();
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
        String fileName = URLEncoder.encode("员工信息", StandardCharsets.UTF_8);
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
        List<Staff> list = reader.readAll(Staff.class);
        staffService.saveBatch(list);
        return Result.success(true);
    }
}