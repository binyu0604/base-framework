package com.liugh.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author liugh
 * @since 2018-05-03
 */
public class JWTToken implements AuthenticationToken {

    // 存储在header中的token名称
    public static final String TOKEN_KEY = "token";

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
