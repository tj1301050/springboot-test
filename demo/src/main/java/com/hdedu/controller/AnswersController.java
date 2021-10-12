package com.hdedu.controller;

import com.hdedu.base.PageResult;
import com.hdedu.base.ResultVO;
import com.hdedu.entity.AnswersEntity;
import com.hdedu.service.AnswersService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "问卷调查")
@RestController
@RequestMapping("/answers")
public class AnswersController {

    @Autowired
    private AnswersService answersService;

    @ApiOperation("根据问卷id查询问卷信息")
    @GetMapping("/get")
    public ResultVO<AnswersEntity> getAnswers(@RequestParam("answerId") Long answerId){
        return ResultVO.success(answersService.getAnswers(answerId));
    }

    @ApiOperation("查询所有问卷信息")
    @GetMapping("/list")
    public ResultVO<List<AnswersEntity>> ListAnswers(){
        return ResultVO.success(answersService.listAnswers());
    }

    @ApiOperation("分页查询所有问卷信息")
    @GetMapping("/page")
    public ResultVO<PageResult<AnswersEntity>> pageAnswers(AnswersEntity answersEntity,
                                                          @ApiParam(name = "pageNumber",defaultValue = "1") @RequestParam Integer pageNumber,
                                                          @ApiParam(name = "pageSize", defaultValue = "10") @RequestParam Integer pageSize){
        return ResultVO.success(answersService.pageAnswers(answersEntity,pageNumber,pageSize));
    }


}
