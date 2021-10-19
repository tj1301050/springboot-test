package com.hdedu.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author EDZ
 * @className UserAuthencationProvider
 * @description TODO
 * @date 2021/10/15 9:31
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户名
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}