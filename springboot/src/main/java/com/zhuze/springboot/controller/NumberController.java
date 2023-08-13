package com.zhuze.springboot.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.INumberService;
import com.zhuze.springboot.entity.Number;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-04
 */
@RestController
@RequestMapping("/number")
public class NumberController {

    @Autowired
    private INumberService numberService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Number number) {
        return Result.success(numberService.saveOrUpdate(number));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(numberService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(numberService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(numberService.list());
    }

    //查询智能柜对应的智能柜号
    @GetMapping("/numbers/{cabinetId}")
    public Result findNumbers(@PathVariable Integer cabinetId) {
        QueryWrapper<Number> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinetId);
        queryWrapper.eq("number_is_empty",1);
        return Result.success(numberService.list(queryWrapper));
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(numberService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "0") Integer cabinetId,
                           @RequestParam(defaultValue = "0") Integer cabinetNumber,
                           @RequestParam(defaultValue = "") String orderNumber,
                           @RequestParam(required = true,defaultValue= "" ) Boolean numberIsWork,
                           @RequestParam(required = true,defaultValue= "" ) Boolean numberIsEmpty) {
        QueryWrapper<Number> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("cabinet_id");
        if(cabinetId != 0) {
            queryWrapper.eq("cabinet_id", cabinetId);
        }
        if(cabinetNumber != 0) {
            queryWrapper.eq("cabinet_number", cabinetNumber);
        }
        if(numberIsWork != null) {
            queryWrapper.eq("number_is_work",numberIsWork);
        }
        if(numberIsEmpty != null) {
            queryWrapper.eq("number_is_empty",numberIsEmpty);
        }
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        return Result.success(numberService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Number> list = numberService.list();
        //通过工具类创建writer写出到磁盘路径

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("智能柜货物信息", StandardCharsets.UTF_8);
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
        List<Number> list = reader.readAll(Number.class);
        numberService.saveBatch(list);
        return Result.success(true);
    }
}

