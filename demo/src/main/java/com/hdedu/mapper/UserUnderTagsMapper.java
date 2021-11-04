package com.hdedu.mapper;

import com.hdedu.entity.UserUnderTagsEntity;

import java.util.List;

public interface UserUnderTagsMapper {

    void batchInsertUserUnderTags(List<UserUnderTagsEntity> list);
}
