package com.hdedu.schedule;

import com.hdedu.service.WechatCustomerAndFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tianjian
 * @className GetAddWechatFriendsSchedule
 * @description TODO
 * @date 2021/11/8 10:03
 */

@Component
@Configuration
@EnableScheduling
public class GetAddWechatFriendsSchedule {

    @Autowired
    private WechatCustomerAndFriendsService wechatCustomerAndFriendsService;

    @Scheduled(cron = "0 * 7 * * ?")
    public void addWechatFriendsSchedule(){
        wechatCustomerAndFriendsService.getAddWechatCustomerAndFriendsInfo();
    }
}