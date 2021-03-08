package com.imooc.security.config;

import com.imooc.security.entity.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证token的过滤器，将token的用户信息存放在session中
 */
@Component
@Slf4j
public class OAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("oauth start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        if (!StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            return null;
        }

        try {
            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);
        } catch (Exception e) {
            log.error("get token info fail", e);
        }

        return null;
    }



    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, "Bearer ");
        String oauthServerUrl = "http://localhost:9090/oauth/check_token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        log.info("token = {}", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oauthServerUrl, HttpMethod.POST, entity, TokenInfo.class);


        log.info("去认证中心验证的返回值 = {}", responseEntity.getBody());
        return responseEntity.getBody();
    }


}
