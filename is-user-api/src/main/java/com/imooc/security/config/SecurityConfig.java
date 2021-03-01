package com.imooc.security.config;

import com.imooc.security.filter.AclInterceptor;
import com.imooc.security.filter.AuditLogInterceptor;
import com.imooc.security.vo.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final AuditLogInterceptor auditLogInterceptor;

    private final AclInterceptor aclInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of("jojo");
        return () -> {
            try {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                UserInfo userInfo = (UserInfo) servletRequestAttributes.getRequest().getSession().getAttribute("user");
                String username = null;
                if (userInfo != null) {
                    username = userInfo.getUsername();
                }
                return Optional.ofNullable(username);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        };
    }
}
