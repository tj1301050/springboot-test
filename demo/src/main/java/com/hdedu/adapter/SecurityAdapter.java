//package com.hdedu.adapter;
//
//import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
///**
// * @author EDZ
// * @className SecurityAdapter
// * @description TODO
// * @date 2021/10/14 15:06
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityAdapter extends WebSecurityEnablerConfiguration  {
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()            // 对请求经行验证
//                .antMatchers("").permitAll()
//                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")     // 必须有admin权限
//                .antMatchers("/user/**").hasAnyRole("ROLE_USER","ROLE_ADMIN") // user或者admin任意一种权限
//                .anyRequest()               //任意请求(这里主要指方法)
//                .authenticated()            // 需要身份认证
//                .and()                      // 表示一个配置的结束
//                .formLogin().permitAll()    //开启SpringSecurity内置的表单登录，会提供一个/login接口
//                .and()
//                .logout().permitAll()       //开启SpringSecurity内置的退出登录，会为我们提供一个/logout接口
//                .and()
//                .csrf().disable();          //关闭csrf跨站伪造请求
//    }
//}