package com.imooc.security.service;

import com.imooc.security.user.User;
import com.imooc.security.vo.UserInfo;

public interface UserService {

    UserInfo user(Long id);

    void save(UserInfo userInfo);

    User findByUsername(String username);
}
