package com.hdedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
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
        PageResult pageResult = new PageResult();
        Page page = PageHelper.startPage(pageNum,pageSize,true);
        QueryWrapper<AnswersEntity> wrapper = new QueryWrapper<>();
        List<AnswersEntity> answersEntities = answersMapper.selectList(wrapper);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setList(answersEntities);
        return pageResult;
    }
}
