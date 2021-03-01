package com.imooc.security.filter;


import com.imooc.security.user.User;
import com.imooc.security.vo.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclInterceptor implements HandlerInterceptor {

    private String[] permitUrls = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;

        System.out.println(4);
        if (!ArrayUtils.contains(permitUrls, request.getRequestURI())) {

            UserInfo user = (UserInfo) request.getSession().getAttribute("user");
            if (user == null) {
                response.setContentType("text/plain");
                response.getWriter().write("need authentication");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = false;
            } else {

                String method = request.getMethod();

                if (!user.hasPermission(method)) {
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;
                }
            }
        }


        return result;
    }
}
