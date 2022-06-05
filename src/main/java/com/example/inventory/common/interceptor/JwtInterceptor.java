package com.example.inventory.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.inventory.common.Constants;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.exception.ServiceException;
import com.example.inventory.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StaffService staffService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        // 获取token中的 staffid
        String staffId;
        try{
            staffId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }

        // 根据 token 中的 staffid 查询数据
        Staff staff = staffService.getById(staffId);

        if(staff == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(staff.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        return true;
    }
}
