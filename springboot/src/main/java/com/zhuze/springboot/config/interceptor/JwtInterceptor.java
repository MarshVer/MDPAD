package com.zhuze.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zhuze.springboot.common.Constants;
import com.zhuze.springboot.entity.Staff;
import com.zhuze.springboot.exception.ServiceException;
import com.zhuze.springboot.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: JwtInterceptor
 * Package: com.zhuze.springboot.common.interceptor
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/5/3 13:06
 * @Version 1.0
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IStaffService staffService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if( StrUtil.isBlank(token) ) {
            throw new ServiceException(Constants.CODE_401, "无token，请重新登陆");
        }
        //获取token中的staffId
        String staffId;
        try {
            staffId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        //验证用户信息是否存在
        Staff staff = staffService.getById(staffId);
        if (staff == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }
        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(staff.getStaffPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch(JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }

        return true;
    }
}
