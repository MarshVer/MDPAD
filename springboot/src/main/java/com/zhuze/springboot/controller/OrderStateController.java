package com.zhuze.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Order;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IOrderStateService;
import com.zhuze.springboot.entity.OrderState;

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
@RequestMapping("/orderState")
public class OrderStateController {

    @Autowired
    private IOrderStateService orderStateService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody OrderState orderState) {
        return Result.success(orderStateService.saveOrUpdate(orderState));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(orderStateService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(orderStateService.removeBatchByIds(ids));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(orderStateService.list());
    }

    //查询正在进行的订单状态
    @GetMapping("/ingState")
    public Result findIngStates() {
        QueryWrapper<OrderState> queryWrapper = new QueryWrapper<>();
        queryWrapper.notBetween("order_flag",1,3);
        return Result.success(orderStateService.list(queryWrapper));
    }

    //查询可以修改的正在进行的订单状态
    @GetMapping("/ingOrderStates")
    public Result findIngOrderStates() {
        QueryWrapper<OrderState> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_flag",5,6,3);
        return Result.success(orderStateService.list(queryWrapper));
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderStateService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String orderState,
                           @RequestParam(defaultValue = "0") Integer orderFlag) {
        QueryWrapper<OrderState> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_flag");
        if(orderFlag != 0) {
            queryWrapper.eq("order_flag", orderFlag);
        }
        queryWrapper.like(!Strings.isEmpty(orderState),"order_state",orderState);
        return Result.success(orderStateService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}

