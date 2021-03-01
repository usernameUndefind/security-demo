package com.imooc.security.filter;

import com.imooc.security.log.AuditLog;
import com.imooc.security.repository.AuditLogRepository;
import com.imooc.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 审计日志
 */
@Component
@AllArgsConstructor
public class AuditLogInterceptor implements HandlerInterceptor {


    private final AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(3);
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());

        auditLogRepository.save(log);
        request.setAttribute("auditLogId", log.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Long auditLogId = (Long) request.getAttribute("auditLogId");

        AuditLog log = auditLogRepository.findById(auditLogId).get();

        log.setStatus(response.getStatus());

        auditLogRepository.save(log);

    }

}
