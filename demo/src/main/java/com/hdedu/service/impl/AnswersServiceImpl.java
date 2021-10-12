package com.hdedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdedu.base.PageResult;
import com.hdedu.entity.AnswersEntity;
import com.hdedu.mapper.AnswersMapper;
import com.hdedu.service.AnswersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersServiceImpl implements AnswersService {

    @Autowired
    private AnswersMapper answersMapper;

    @Override
    public AnswersEntity getAnswers(Long id) {
        return answersMapper.selectById(id);
    }

    @Override
    public List<AnswersEntity> listAnswers() {
        QueryWrapper<AnswersEntity> wrapper = new QueryWrapper<>();
        return answersMapper.selectList(wrapper);
    }


    /**
     *
     * @param answersEntity
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult pageAnswers(AnswersEntity answersEntity,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<AnswersEntity> wrapper = new QueryWrapper<>();
        List<AnswersEntity> answersEntities = answersMapper.selectList(wrapper);
        PageInfo pageInfo = new PageInfo(answersEntities);
        PageResult pageInfo1 = new PageResult();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(answersEntities);
        return pageInfo1;
    }
}
