package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.service.WechatCustomerAndFriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author tianjian
 * @className GetWechatHouseKeeperAccessToken
 * @description TODO
 * @date 2021/10/28 9:22
 */
@Api(tags = "客户管家-客户管理-好友分配")
@RestController
@RequestMapping("/wechat/houseKeeper")
@Component
public class GetWechatHouseKeeperAccessTokenController {

    @Autowired
    private WechatCustomerAndFriendsService wechatCustomerAndFriendsService;


    /**
     * 获取公司在微管家的用户信息并作存储
     * 数据爬取过一次，暂时关闭，有需要的话重新开启
     * @return
     */
//    @ApiOperation("获取小助手和线索用户关系并存储")
//    @GetMapping("/getWechatFriendsAndSave")
    public ResultVO getWechatFriendsAndSave() {
        wechatCustomerAndFriendsService.batchInsertWechatCustomerAndFriendsInfo();
        return ResultVO.success();
    }

    @ApiOperation("获取增量小助手和线索用户关系并存储")
    @GetMapping("/getAddWechatFriendsAndSave")
    public ResultVO getAddWechatFriendsAndSave(){
        wechatCustomerAndFriendsService.getAddWechatCustomerAndFriendsInfo();
        return ResultVO.success();
    }

    @ApiOperation("定时更新未分配小助手的线索")
    @GetMapping("/updateAddWechatFriendsOnTime")
    public ResultVO updateAddWechatFriendsOnTime(){
        wechatCustomerAndFriendsService.getLastWechatCustomerAndFriendsInfoOnTime();
        return ResultVO.success();
    }

}