package com.hdedu.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

    UserDetails loadUserByUserName(String username);
}
