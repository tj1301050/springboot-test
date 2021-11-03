package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.service.WechatFriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianjian
 * @className WechatFriendsController
 * @description TODO
 * @date 2021/11/3 9:07
 */
@Api(tags = "客服管家-微信列表")
@RestController
@RequestMapping("/wechat")
public class WechatFriendsController {

    @Autowired
    private WechatFriendsService wechatFriendsService;

    /**
     * 获取客服管家页面-微信列表信息
     * @return
     */
    @ApiOperation("获取客服的微信信息")
    @GetMapping("/getCustomerWechatInfo")
    public ResultVO getCustomerWechatInfo() {
        wechatFriendsService.getWechatFriends();
        return ResultVO.success();
    }
}