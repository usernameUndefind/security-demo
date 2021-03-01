package com.imooc.security.controller;

import com.imooc.security.service.UserService;
import com.imooc.security.user.User;
import com.imooc.security.vo.UserInfo;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public void login(@Validated UserInfo userInfo , HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        User user = userService.findByUsername(userInfo.getUsername());
        if (user != null && SCryptUtil.check(userInfo.getPassword(), user.getPassword())) {
            request.getSession().setAttribute("user", user.buildInfo());
        }
    }

    @PostMapping("/logout")
    public void login(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user) {
        userService.save(user);
        return user;
    }


    @GetMapping("/{id}")
    public UserInfo user(@PathVariable Long id, HttpServletRequest request) {
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (user == null || !user.getId().equals(id)) {
            throw new RuntimeException("身份认证信息异常, 获取用户信息失败");
        }
        return userService.user(id);
    }
}
