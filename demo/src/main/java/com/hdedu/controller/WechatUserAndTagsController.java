package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.service.WechatFriendsAndTagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianjian
 * @className WechatUserAndTagsController
 * @description TODO
 * @date 2021/11/3 19:25
 */
@Api(tags = "微信列表-标签-标签下用户关系")
@RestController
@RequestMapping("/wechat/tags/")
public class WechatUserAndTagsController {

    @Autowired
    private WechatFriendsAndTagsService wechatFriendsAndTagsService;

    /**
     * 存储微信列表-标签-标签下的好友关系
     * @param str cookie
     * @return
     */
    @ApiOperation("获取客服-标签-标签下的用户的微信信息")
    @GetMapping("/user/relationship")
    public ResultVO getCustomerWechatInfo(String str) {
        wechatFriendsAndTagsService.batchInsertTags(str);
        return ResultVO.success();
    }
}