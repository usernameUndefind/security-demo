package com.imooc.security.repository;

import com.imooc.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

    User findByUsername(String username);
}
