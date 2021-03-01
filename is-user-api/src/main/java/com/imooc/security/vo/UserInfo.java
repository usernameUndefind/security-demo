package com.imooc.security.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class UserInfo {

    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String permissions;


    public boolean hasPermission(String method) {
        if ("get".equalsIgnoreCase(method)) {
            return StringUtils.contains(permissions, "r");
        } else {
            return StringUtils.contains(permissions, "w");
        }
    }
}
