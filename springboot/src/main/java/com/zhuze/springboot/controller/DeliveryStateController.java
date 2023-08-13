package com.zhuze.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IDeliveryStateService;
import com.zhuze.springboot.entity.DeliveryState;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/deliveryState")
public class DeliveryStateController {

    @Autowired
    private IDeliveryStateService deliveryStateService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody DeliveryState deliveryState) {
        return Result.success(deliveryStateService.saveOrUpdate(deliveryState));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(deliveryStateService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(deliveryStateService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(deliveryStateService.list());
    }

    //查询进行中的配送状态
    @GetMapping("/deliveryIngStates")
    public Result findIngStates() {
        QueryWrapper<DeliveryState> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("delivery_flag", 1);
        return Result.success(deliveryStateService.list(queryWrapper));
    }

    //查询结束的配送状态
    @GetMapping("/deliveryEndStates")
    public Result findEndStates() {
        QueryWrapper<DeliveryState> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("delivery_flag", 1,2);
        return Result.success(deliveryStateService.list(queryWrapper));
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(deliveryStateService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String deliveryState,
                           @RequestParam(defaultValue = "0") Integer deliveryFlag) {
        QueryWrapper<DeliveryState> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("delivery_flag");
        if(deliveryFlag != 0) {
            queryWrapper.eq("delivery_flag", deliveryFlag);
        }
        queryWrapper.like(!Strings.isEmpty(deliveryState),"delivery_state",deliveryState);
        return Result.success(deliveryStateService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
}

