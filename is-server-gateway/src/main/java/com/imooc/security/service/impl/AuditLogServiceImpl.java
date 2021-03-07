package com.imooc.security.service.impl;

import com.imooc.security.entity.AuditLog;
import com.imooc.security.repository.AuditLogRepository;
import com.imooc.security.service.AuditLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {


    private final AuditLogRepository auditLogRepository;

    @Override
    public int updateAuditLogStatus(Long id, Integer status) {
        AuditLog auditLog = auditLogRepository.findById(id).get();
        auditLog.setStatus(status);
        auditLogRepository.save(auditLog);
        return 1;
    }
}
