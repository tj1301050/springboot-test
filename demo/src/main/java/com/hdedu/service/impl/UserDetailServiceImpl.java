package com.hdedu.service.impl;

import com.hdedu.service.UserDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author EDZ
 * @className UserDetaulServiceImpl
 * @description TODO
 * @date 2021/10/14 15:28
 */
@Configuration
public class UserDetailServiceImpl implements UserDetailService {
    @Override
    public UserDetails loadUserByUserName(String username) {
        if (StringUtils.isBlank(username)){
            return null;
        }
        if (username.equals("hahah")){

        }
        return null;
    }
}