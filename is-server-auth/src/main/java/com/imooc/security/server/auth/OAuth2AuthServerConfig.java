package com.imooc.security.server.auth;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * OAuth2配置 认证服务器
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 客户端应用详情信息（客户端）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存配置客户端
//        clients.inMemory()
//                .withClient("orderApp")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("read", "write") // 访问能干什么，是读还是写
//                .accessTokenValiditySeconds(3600) // token失效时间
//                .resourceIds("order-server") // 能访问哪些资源服务器
//                .authorizedGrantTypes("password") // 可以使用哪种授权类型，  一共有四种授权类型
//                .and() // 接着配置一个资源服务器
//
//                .withClient("orderService")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("read", "write") // 访问能干什么，是读还是写
//                .accessTokenValiditySeconds(3600) // token失效时间
//                .resourceIds("order-server") // 能访问哪些资源服务器
//                .authorizedGrantTypes("password"); // 可以使用哪种授权类型，  一共有四种授权类型
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 负责校验传进来的用户信息是不是合法的
        endpoints.tokenStore(tokenStore())
                 .authenticationManager(authenticationManager);
    }
}
