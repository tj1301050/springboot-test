package com.hdedu.service;

public interface WechatCustomerAndFriendsService {

    void batchInsertWechatCustomerAndFriendsInfo();

    void getAddWechatCustomerAndFriendsInfo();

    /**
     * 同步没有分配客服的线索(account_id为0)
     */
    void getLastWechatCustomerAndFriendsInfoOnTime();
}
