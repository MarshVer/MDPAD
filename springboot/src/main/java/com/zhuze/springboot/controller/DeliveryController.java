package com.zhuze.springboot.controller;

import ch.qos.logback.core.status.OnPrintStreamStatusListenerBase;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Drone;
import com.zhuze.springboot.entity.Number;
import com.zhuze.springboot.entity.Order;
import com.zhuze.springboot.service.IDroneService;
import com.zhuze.springboot.service.INumberService;
import com.zhuze.springboot.service.IOrderService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuze.springboot.service.IDeliveryService;
import com.zhuze.springboot.entity.Delivery;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-05-12
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private IDroneService droneService;

    @Autowired
    private INumberService numberService;

    @Autowired
    private IOrderService orderService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody Delivery delivery) {
        return Result.success(deliveryService.saveOrUpdate(delivery));
    }

    //分配
    @PostMapping("/allocate")
    public Result allocate(@RequestBody Delivery delivery) {
        //更新无人机工作状态
        QueryWrapper<Drone> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("drone_id", delivery.getDroneId());
        Drone drone = new Drone();
        drone.setWorkingFlag(2);
        droneService.saveOrUpdate(drone,queryWrapper1);

        //更新智能柜柜号信息
        QueryWrapper<Number> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("cabinet_id", delivery.getCabinetId());
        queryWrapper2.eq("cabinet_number", delivery.getCabinetNumber());
        Number number = new Number();
        number.setOrderNumber(delivery.getOrderNumber());
        number.setNumberIsEmpty(false);
        numberService.saveOrUpdate(number,queryWrapper2);

        //更新订单状态
        QueryWrapper<Order> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("order_number", delivery.getOrderNumber());
        Order order = new Order();
        order.setOrderFlag(5);
        orderService.saveOrUpdate(order,queryWrapper3);

        return Result.success(deliveryService.saveOrUpdate(delivery));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(deliveryService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(deliveryService.removeBatchByIds(ids));
    }

    //撤销
    @PostMapping("/revokeOne")
    public Result revoke(@RequestBody Delivery delivery) {
        //更新无人机工作状态
        QueryWrapper<Drone> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("drone_id", delivery.getDroneId());
        Drone drone = new Drone();
        drone.setWorkingFlag(1);
        droneService.saveOrUpdate(drone,queryWrapper1);

        //更新智能柜柜号信息
        QueryWrapper<Number> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("cabinet_id", delivery.getCabinetId());
        queryWrapper2.eq("cabinet_number", delivery.getCabinetNumber());
        Number number = new Number();
        number.setOrderNumber("");
        number.setNumberIsEmpty(true);
        numberService.saveOrUpdate(number,queryWrapper2);

        //更新订单状态
        QueryWrapper<Order> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("order_number", delivery.getOrderNumber());
        Order order = new Order();
        order.setOrderFlag(2);
        orderService.saveOrUpdate(order,queryWrapper3);

        delivery.setEndDate(LocalDateTime.now());
        delivery.setDeliveryFlag(1);
        deliveryService.saveOrUpdate(delivery);
        return Result.success();
    }

    //批量撤销
    @PostMapping("/revoke/batch")
    public Result revokeBatch(@RequestBody List<Delivery> deliveries) {
        for (Delivery delivery :deliveries) {
            //更新无人机工作状态
            QueryWrapper<Drone> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("drone_id", delivery.getDroneId());
            Drone drone = new Drone();
            drone.setWorkingFlag(1);
            droneService.saveOrUpdate(drone,queryWrapper1);

            //更新智能柜柜号信息
            QueryWrapper<Number> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("cabinet_id", delivery.getCabinetId());
            queryWrapper2.eq("cabinet_number", delivery.getCabinetNumber());
            Number number = new Number();
            number.setOrderNumber("");
            number.setNumberIsEmpty(true);
            numberService.saveOrUpdate(number,queryWrapper2);

            //更新订单状态
            QueryWrapper<Order> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("order_number", delivery.getOrderNumber());
            Order order = new Order();
            order.setOrderFlag(2);
            orderService.saveOrUpdate(order,queryWrapper3);

            delivery.setEndDate(LocalDateTime.now());
            delivery.setDeliveryFlag(1);
            deliveryService.saveOrUpdate(delivery);
        }
        return Result.success();
    }

    //查询
    @GetMapping
    public Result findAll() {
        return Result.success(deliveryService.list());
    }



    //id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(deliveryService.getById(id));
    }


    //正在进行的配送分页查询
    @GetMapping("/deliveryIng")
    public Result findDeliveryIng(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String orderNumber) {
        QueryWrapper<Delivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("delivery_id");
        queryWrapper.notBetween("delivery_flag",1,2);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        return Result.success(deliveryService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //结束的分配分页查询
    @GetMapping("/deliveryEnd")
    public Result findDeliveryEnd(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String orderNumber,
                                  @RequestParam(defaultValue = "0") Integer deliveryFlag) {
        QueryWrapper<Delivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("delivery_id");
        queryWrapper.in("delivery_flag",1,2);
        queryWrapper.like(!Strings.isEmpty(orderNumber),"order_number",orderNumber);
        if(deliveryFlag != 0) {
            queryWrapper.eq("delivery_flag",deliveryFlag);
        }
        return Result.success(deliveryService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<Delivery> list = deliveryService.list();
        //通过工具类创建writer写出到磁盘路径

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内的队形到excel，使用默认样式，强制输出到标题
        writer.write(list,true);

        //设置浏览器相应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("信息", StandardCharsets.UTF_8);
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
        List<Delivery> list = reader.readAll(Delivery.class);
        deliveryService.saveBatch(list);
        return Result.success(true);
    }
}

