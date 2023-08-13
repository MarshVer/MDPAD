package com.zhuze.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhuze.springboot.entity.Staff;
import com.zhuze.springboot.service.IStaffService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * ClassName: TokenUtils
 * Package: com.zhuze.springboot.utils
 * Description: 生成token
 *
 * @Author 朱泽
 * @Create 2023/5/3 11:53
 * @Version 1.0
 */
@Component
public class TokenUtils {

    private static IStaffService staticStaffService;

    @Resource
    private IStaffService staffService;

    @PostConstruct
    public void setStaffService() {
        staticStaffService = staffService;
    }

    public static String genToken(String staffId, String sign) {
        return JWT.create().withAudience(staffId) //将staffId保存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //2小时后token过期
                .sign(Algorithm.HMAC256(sign));     //以password作为token的密钥
    }
    //获取当前登录的用户信息
    public static Staff getCurrentStaff() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)) {
                String staffId = JWT.decode(token).getAudience().get(0);
                return staticStaffService.getById(Integer.valueOf(staffId));
            }
        } catch (Exception e) {
                return null;
        }
        return null;
    }
}
