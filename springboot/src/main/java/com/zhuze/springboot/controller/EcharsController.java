package com.zhuze.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.date.Quarter;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuze.springboot.common.Result;
import com.zhuze.springboot.entity.Order;
import com.zhuze.springboot.entity.Staff;
import com.zhuze.springboot.entity.User;
import com.zhuze.springboot.service.IOrderService;
import com.zhuze.springboot.service.IStaffService;
import com.zhuze.springboot.service.IUserService;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: EcharsController
 * Package: com.zhuze.springboot.controller
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/5/3 21:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/echarts")
public class EcharsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IStaffService staffService;

    @Autowired
    private IOrderService orderService;


    @GetMapping("/orders")
    public Result orders() {
        List<Order> orderList = orderService.list();
        //月份
        int m1 = 0;
        int m2 = 0;
        int m3 = 0;
        int m4 = 0;
        int m5 = 0;
        int m6 = 0;
        int m7 = 0;
        int m8 = 0;
        int m9 = 0;
        int m10 = 0;
        int m11= 0;
        int m12 = 0;
        for(Order order : orderList) {
            Date createTime = order.getBillingDate();
            Month month = DateUtil.monthEnum(createTime);
            switch (month) {
                case JANUARY: m1++;break;
                case FEBRUARY: m2++;break;
                case MARCH: m3++;break;
                case APRIL: m4++;break;
                case MAY: m5++;break;
                case JUNE: m6++;break;
                case JULY: m7++;break;
                case AUGUST: m8++;break;
                case SEPTEMBER: m9++;break;
                case OCTOBER: m10++;break;
                case NOVEMBER: m11++;break;
                case DECEMBER: m12++;break;
                default: break;
            }
        }
        return  Result.success(CollUtil.newArrayList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12));
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0;     //第一季度
        int q2 = 0;     //第二季度
        int q3 = 0;     //第三季度
        int q4 = 0;     //第四季度
        for (User user : list) {
            Date createTime = user.getUserCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1++;break;
                case Q2: q2++;break;
                case Q3: q3++;break;
                case Q4: q4++;break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @GetMapping("/staffType")
    public Result staffType() {
        List<Staff> list = staffService.list();
        int t1 = 0;
        int t2 = 0;
        int t3 = 0;
        int t4 = 0;
        for (Staff staff : list) {
            if (staff.getPosition().equals( 1)) {
                t1++;
            } else if (staff.getPosition().equals( 2)) {
                t2++;
            } else if (staff.getPosition().equals( 3)) {
                t3++;
            } else if (staff.getPosition().equals( 4)) {
                t4++;
            }
        }
        return Result.success(CollUtil.newArrayList(t1, t2, t3, t4));
    }

}
