package com.imooc.security.filter;

import com.imooc.security.repository.UserRepository;
import com.imooc.security.user.User;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证
 */
@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println(2);
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (StringUtils.isNotBlank(authHeader)) {

            String token = StringUtils.substringAfter(authHeader, "Basic ");

            String token64 = new String(Base64Utils.decodeFromString(token));

            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token64, ":");

            String username = items[0];
            String password = items[1];

            User user = repository.findByUsername(username);

            if (user != null && SCryptUtil.check(password, user.getPassword())) {
                httpServletRequest.getSession().setAttribute("user", user.buildInfo());
                httpServletRequest.getSession().setAttribute("temp", "yes");
            }
        }

        try {

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        } finally {
            if (httpServletRequest.getSession().getAttribute("temp") != null) {
                httpServletRequest.getSession().invalidate();
            }
        }

    }
}
