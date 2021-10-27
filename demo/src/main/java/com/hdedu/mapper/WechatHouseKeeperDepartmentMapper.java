package com.hdedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdedu.entity.WechatHouseKeeperDepartmentEntity;

import java.util.List;

public interface WechatHouseKeeperDepartmentMapper extends BaseMapper<WechatHouseKeeperDepartmentEntity> {

    int selectCountByDepartmentId(String departmentId);

    int bathInsertDepartment(List<WechatHouseKeeperDepartmentEntity> list);
}
