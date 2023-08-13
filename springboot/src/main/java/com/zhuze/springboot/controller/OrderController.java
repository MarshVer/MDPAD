package com.zhuze.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Delivery;
import com.zhuze.springboot.entity.Drone;
import com.zhuze.springboot.mapper.OrderMapper;
import com.zhuze.springboot.service.IDeliveryService;
import com.zhuze.springboot.service.IDroneService;
import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IOrderService;
import com.zhuze.springboot.entity.Order;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDroneService droneService;

    @Autowired
    private IDeliveryService deliveryService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Order order) {
        return Result.success(orderService.saveOrUpdate(order));
    }

    //配送完成修改订单、无人机状态以及添加配送结束时间
    @PostMapping("/over")
    public Result overOrder(@RequestBody Delivery delivery) {
        //修改订单状态
        QueryWrapper<Order> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order_number", delivery.getOrderNumber());
        Order order = new Order();
        order.setOrderFlag(6);
        orderService.saveOrUpdate(order,queryWrapper1);

        //修改无人机状态
        QueryWrapper<Drone> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("drone_id", delivery.getDroneId());
        Drone drone = new Drone();
        drone.setWorkingFlag(1);
        droneService.saveOrUpdate(drone, queryWrapper2);

        //增加配送结束时间
        delivery.setEndDate(LocalDateTime.now());
        deliveryService.saveOrUpdate(delivery);

        return Result.success();
    }


    //撤回
    @PostMapping("/revokeOne")
    public Result revoke(@RequestBody Order order) {
        order.setOrderFlag(2);
        order.setEndDate(LocalDateTime.now());
        orderService.saveOrUpdate(order);
        return Result.success();
    }

    //批量撤销
    @PostMapping("/revoke/batch")
    public Result revokeBatch(@RequestBody List<Order> orders) {
        for (Order order :orders) {
            order.setOrderFlag(2);
            order.setEndDate(LocalDateTime.now());
            orderService.saveOrUpdate(order);
        }
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(orderService.removeById(id));
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(orderService.removeBatchByIds(ids));
    }


    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(orderService.list());
    }


    //查询数量
    @GetMapping("/count")
    public Result findCount() {
        return Result.success(orderService.count());
    }

    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderService.getById(id));
    }


    //微信小程序查询用户所有订单
    @GetMapping("/allOrder")
    public Result findAllOrder(@RequestParam String userPhone) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consignor_phone", userPhone).or().eq("consignee_phone",userPhone);
        queryWrapper.orderByDesc("billing_date");
        return Result.success(orderService.list(queryWrapper));
    }


    //微信小程序查询用户正在进行的订单
    @GetMapping("/ingOrder")
    public Result findIngOrder(@RequestParam String userPhone) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("order_flag",2,3).and(wq -> wq.eq("consignor_phone", userPhone).or().eq("consignee_phone",userPhone));
        queryWrapper.orderByDesc("billing_date");
        return Result.success(orderService.list(queryWrapper));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_id");
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //未受理分页查询
    @GetMapping("/startPage")
    public Result findStartPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String orderNumber,
                                @RequestParam(defaultValue = "") String consignorPhone,
                                @RequestParam(defaultValue = "") String consigneePhone) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_id");
        queryWrapper.eq("order_flag",1);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        queryWrapper.like(!Strings.isEmpty(consignorPhone),"consignor_phone",consignorPhone);
        queryWrapper.like(!Strings.isEmpty(consigneePhone),"consignee_phone",consigneePhone);
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    //正在进行的分页查询
    @GetMapping("/ingPage")
    public Result findIngPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String orderNumber,
                                @RequestParam(defaultValue = "") String consignorPhone,
                                @RequestParam(defaultValue = "") String consigneePhone,
                                @RequestParam(defaultValue = "") String orderFlag) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_id");
        queryWrapper.notBetween("order_flag",1,3);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        queryWrapper.like(!Strings.isEmpty(consignorPhone),"consignor_phone",consignorPhone);
        queryWrapper.like(!Strings.isEmpty(consigneePhone),"consignee_phone",consigneePhone);
        queryWrapper.like(!Strings.isEmpty(orderFlag),"order_flag",orderFlag);
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    //已完成分页查询
    @GetMapping("/endPage")
    public Result findEndPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String orderNumber,
                              @RequestParam(defaultValue = "") String consignorPhone,
                              @RequestParam(defaultValue = "") String consigneePhone,
                              @RequestParam(defaultValue = "") String orderFlag
                              ) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("end_date");
        queryWrapper.eq("order_flag",2).or().eq("order_flag",3);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        queryWrapper.like(!Strings.isEmpty(consignorPhone),"consignor_phone",consignorPhone);
        queryWrapper.like(!Strings.isEmpty(consigneePhone),"consignee_phone",consigneePhone);
        queryWrapper.like(!Strings.isEmpty(orderFlag),"order_flag",orderFlag);
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //新建配送的分页查询
    @GetMapping("/deliveryStart")
    public Result findDeliveryStart(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String orderNumber,
                                    @RequestParam(defaultValue = "") String consignorPhone,
                                    @RequestParam(defaultValue = "") String consigneePhone) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_id");
        queryWrapper.eq("order_flag",4);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        queryWrapper.like(!Strings.isEmpty(consignorPhone),"consignor_phone",consignorPhone);
        queryWrapper.like(!Strings.isEmpty(consigneePhone),"consignee_phone",consigneePhone);
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Order> list = orderService.list();
        //通过工具类创建writer写出到磁盘路径

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("订单信息", StandardCharsets.UTF_8);
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
        List<Order> list = reader.readAll(Order.class);
        orderService.saveBatch(list);
        return Result.success(true);
    }
}

