package com.hdedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdedu.entity.WechatHouseKeeperUserEntity;

import java.util.List;

public interface WechatHouseKeeperUserMapper extends BaseMapper<WechatHouseKeeperUserEntity> {

    int bathInsertUser(List<WechatHouseKeeperUserEntity> list);

    int selectCountByWechatHouseKeeperUserId(String wechatHouseKeeperUserId);
}
