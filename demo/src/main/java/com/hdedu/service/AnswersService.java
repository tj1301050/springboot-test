package com.hdedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hdedu.base.PageResult;
import com.hdedu.entity.AnswersEntity;

import java.util.List;

public interface AnswersService {

    AnswersEntity getAnswers(Long id);

    List<AnswersEntity> listAnswers();

    PageResult pageAnswers(AnswersEntity answersEntity, Integer pageNum, Integer pageSize);
}
