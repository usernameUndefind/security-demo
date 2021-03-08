package com.imooc.security.config;

import com.imooc.security.entity.AuditLog;
import com.imooc.security.repository.AuditLogRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 审计日志过滤器
 */
@Component
@Slf4j
@AllArgsConstructor
public class AuditLogFilter extends ZuulFilter {

    private final AuditLogRepository auditLogRepository;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("audit log insert");


        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();

        AuditLog auditLog = new AuditLog();
        auditLog.setMethod(request.getMethod());
        auditLog.setPath(request.getRequestURI());
        auditLogRepository.save(auditLog);

        request.setAttribute("auditLogId", auditLog.getId());

        return null;
    }
}
