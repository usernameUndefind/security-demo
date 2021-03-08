package com.imooc.security.config;

import com.imooc.security.entity.AuditLog;
import com.imooc.security.repository.AuditLogRepository;
import com.imooc.security.service.AuditLogService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 审计修改过滤器
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuditLogAfterFilter extends ZuulFilter {


    private final AuditLogService auditLogService;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletResponse response = currentContext.getResponse();

        HttpServletRequest request = currentContext.getRequest();

        Long auditLogId = (Long) request.getAttribute("auditLogId");

        // 修改审计日志
        new Thread(() -> auditLogService.updateAuditLogStatus(auditLogId, response.getStatus())).start();

        return null;
    }

}
