package com.zhuze.springboot.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.ICabinetService;
import com.zhuze.springboot.entity.Cabinet;

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
@RequestMapping("/cabinet")
public class CabinetController {

    @Autowired
    private ICabinetService cabinetService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Cabinet cabinet) {
        return Result.success(cabinetService.saveOrUpdate(cabinet));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(cabinetService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(cabinetService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(cabinetService.list());
    }

    //查询可以工作
    @GetMapping("/isWork")
    public Result findIsWork() {
        QueryWrapper<Cabinet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_is_work",1);
        return Result.success(cabinetService.list(queryWrapper));
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(cabinetService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "0") Integer cabinetId,
                           @RequestParam(defaultValue = "") String cabinetModel,
                           @RequestParam(required = true,defaultValue= "" ) Boolean cabinetIsWork,
                           @RequestParam(defaultValue = "") String cabinetAddress) {

        QueryWrapper<Cabinet> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("cabinet_id");
        if(cabinetId != 0) {
            queryWrapper.eq("cabinet_id", cabinetId);
        }
        if(cabinetIsWork != null) {
            queryWrapper.eq("cabinet_is_work",cabinetIsWork);
        }
        queryWrapper.like(!Strings.isEmpty(cabinetModel),"cabinet_model",cabinetModel);
        queryWrapper.like(!Strings.isEmpty(cabinetAddress),"cabinet_Address",cabinetAddress);

        return Result.success(cabinetService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Cabinet> list = cabinetService.list();
        //通过工具类创建writer写出到磁盘路径

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("智能柜信息", StandardCharsets.UTF_8);
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
        List<Cabinet> list = reader.readAll(Cabinet.class);
        cabinetService.saveBatch(list);
        return Result.success(true);
    }
}

