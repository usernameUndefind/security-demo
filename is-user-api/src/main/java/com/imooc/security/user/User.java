package com.imooc.security.user;

import com.imooc.security.vo.UserInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    @Column(unique = true)
    private String username;

    private String password;

    private String permissions;


    public UserInfo buildInfo() {
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(this, info);
        return info;
    }

}
