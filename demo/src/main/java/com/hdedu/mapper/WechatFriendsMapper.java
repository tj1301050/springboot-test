package com.hdedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdedu.entity.WechatFriendsEntity;

import java.util.List;

public interface WechatFriendsMapper extends BaseMapper<WechatFriendsEntity> {

    void bathInsertWechatFriends(List<WechatFriendsEntity> list);

    int selectCountByPersonalWechatId(int personalWechatId);

    void batchUpdateWechatFriends(List<WechatFriendsEntity> list);
}
