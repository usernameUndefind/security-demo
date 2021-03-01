package com.imooc.security.service.impl;

import com.imooc.security.repository.UserRepository;
import com.imooc.security.service.UserService;
import com.imooc.security.user.User;
import com.imooc.security.vo.UserInfo;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo user(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public void save(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(), 32768, 8, 1));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
