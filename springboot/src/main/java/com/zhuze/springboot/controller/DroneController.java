package com.zhuze.springboot.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Delivery;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IDroneService;
import com.zhuze.springboot.entity.Drone;

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
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private IDroneService droneService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Drone drone) {
        return Result.success(droneService.saveOrUpdate(drone));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(droneService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(droneService.removeBatchByIds(ids));
    }

    //配送状态改为配送时无人机状态相应改变
    @PostMapping("/droneState")
    public Result deleteBatch(@RequestBody Delivery delivery) {
        //修改无人机状态
        QueryWrapper<Drone> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("drone_id", delivery.getDroneId());
        Drone drone = new Drone();
        drone.setWorkingFlag(3);
        droneService.saveOrUpdate(drone, queryWrapper);
        return Result.success();
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(droneService.list());
    }

    //查询空闲且可以工作的无人机
    @GetMapping("/free")
    public Result findFree() {
        QueryWrapper<Drone> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("working_flag", 1);
        queryWrapper.eq("drone_is_work", 1);
        return Result.success(droneService.list(queryWrapper));
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(droneService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "0") Integer droneId,
                           @RequestParam(defaultValue = "") String droneModel,
                           @RequestParam(defaultValue = "0") Integer workingFlag,
                           @RequestParam(required = true,defaultValue= "" ) Boolean droneIsWork) {
        QueryWrapper<Drone> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("drone_id");
        if(droneId != 0) {
            queryWrapper.eq("drone_id", droneId);
        }
        if(workingFlag != 0) {
            queryWrapper.eq("working_flag", workingFlag);
        }
        if(droneIsWork != null) {
            queryWrapper.eq("drone_is_work",droneIsWork);
        }
        queryWrapper.like(!Strings.isEmpty(droneModel),"drone_model",droneModel);
        return Result.success(droneService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Drone> list = droneService.list();

        //通过工具类创建writer写出到磁盘路径
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("无人机信息", StandardCharsets.UTF_8);
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
        List<Drone> list = reader.readAll(Drone.class);
        droneService.saveBatch(list);
        return Result.success(true);
    }

}

