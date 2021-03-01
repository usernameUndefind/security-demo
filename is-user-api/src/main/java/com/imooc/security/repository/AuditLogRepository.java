package com.imooc.security.repository;

import com.imooc.security.log.AuditLog;
import com.imooc.security.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog, Long> {

}
