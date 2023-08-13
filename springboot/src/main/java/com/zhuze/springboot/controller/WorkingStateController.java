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

import com.zhuze.springboot.service.IWorkingStateService;
import com.zhuze.springboot.entity.WorkingState;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-08
 */
@RestController
@RequestMapping("/workingState")
public class WorkingStateController {

    @Autowired
    private IWorkingStateService workingStateService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody WorkingState workingState) {
        return Result.success(workingStateService.saveOrUpdate(workingState));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(workingStateService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(workingStateService.removeBatchByIds(ids));
    }


    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String workingState,
                           @RequestParam(defaultValue = "0") Integer workingFlag) {
        QueryWrapper<WorkingState> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("working_flag");
        if(workingFlag != 0) {
            queryWrapper.eq("working_flag", workingFlag);
        }
        queryWrapper.like(!Strings.isEmpty(workingState),"working_state",workingState);

        return Result.success(workingStateService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(workingStateService.list());
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(workingStateService.getById(id));
    }

}

