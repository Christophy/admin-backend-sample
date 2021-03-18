package com.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.stream.Collectors;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                            Object handler) {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        UserRole userRole = method.getAnnotation(UserRole.class);
        // 有 @LoginRequired 注解，需要认证
        if (userRole != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String role = userRole.value();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Boolean allow =
                        authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList()).contains(role);
                return allow;
            }
            return true;
        }
        return true;
    }
}
