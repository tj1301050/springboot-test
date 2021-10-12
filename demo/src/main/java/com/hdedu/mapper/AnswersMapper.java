package com.hdedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdedu.entity.AnswersEntity;
import org.apache.ibatis.annotations.Param;

public interface AnswersMapper extends BaseMapper<AnswersEntity> {

    AnswersEntity getAnswersByAnswerId(@Param("id") Long answerId);
}
