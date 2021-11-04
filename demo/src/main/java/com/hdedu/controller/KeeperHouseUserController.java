package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.service.WechatHouseKeeperUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianjian
 * @className KeeperHouseUserController
 * @description TODO
 * @date 2021/10/26 18:43
 */

@Api(tags = "微管家用户信息同步")
@RestController
@RequestMapping("/housekeeper")
public class KeeperHouseUserController {

    @Autowired
    private WechatHouseKeeperUserService wechatHouseKeeperUserService;

    /**
     * 获取公司在微管家的用户信息并作存储
     * @return
     */
    @ApiOperation("获取公司在微管家的用户信息并作存储")
    @GetMapping("/getUserInfoFromWechatHouseKeeperAndSave")
    public ResultVO getUserInfo(String str) {
        wechatHouseKeeperUserService.getWechatHouseKeeperUserInfo(str);
        return ResultVO.success();
    }
}