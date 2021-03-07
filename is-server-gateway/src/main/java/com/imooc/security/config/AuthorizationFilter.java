package com.imooc.security.config;

import com.imooc.security.entity.TokenInfo;
import com.imooc.security.service.AuditLogService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证 token，判断是否可以访问接口 或者是否有权限
 */
@Component
@Slf4j
@AllArgsConstructor
public class AuthorizationFilter extends ZuulFilter {

    private final AuditLogService auditLogService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("authorization start");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 是否需要验证(可设置一些)
        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");

            if (tokenInfo != null && tokenInfo.isActive()) {
                // 判断是否有权限
                if (hasPermission(tokenInfo, request)) {
                    log.info("audit log update fail 403");
                    auditLogService.updateAuditLogStatus((Long) request.getAttribute("auditLogId"), 403);
                    handleError(403, requestContext);
                }
            } else {
                log.info("audit log update fail 401");
                auditLogService.updateAuditLogStatus((Long) request.getAttribute("auditLogId"), 401);
                handleError(401, requestContext);
            }
        }

        return null;
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {

        return RandomUtils.nextInt() % 2 == 0;
    }


    /**
     * 统一异常处理
     * @param status
     * @param requestContext
     */
    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        requestContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        // 只有不是/token 的请求需要验证， 可配置多个，根据业务逻辑来
        return !StringUtils.startsWith(request.getRequestURI(), "/token");
    }
}
