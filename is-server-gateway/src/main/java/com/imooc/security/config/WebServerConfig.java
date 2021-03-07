package com.imooc.security.config;

import com.imooc.security.entity.TokenInfo;
import com.netflix.zuul.context.RequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class WebServerConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            try {

                return Optional.ofNullable("liu");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
