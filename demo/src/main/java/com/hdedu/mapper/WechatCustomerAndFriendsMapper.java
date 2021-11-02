package com.hdedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdedu.entity.WechatCustomerAndFriendsEntity;

import java.util.List;

public interface WechatCustomerAndFriendsMapper extends BaseMapper<WechatCustomerAndFriendsEntity> {

    void batchInsertWechatCustomerAndFriendsInfo(List<WechatCustomerAndFriendsEntity> list);

    String getLastUpdateTime();

    List<Integer> getLastWechatCustomerAndFriendsInfoOnTime();

    void updateWechatCustomerAndFriends(List<WechatCustomerAndFriendsEntity> list);
}
