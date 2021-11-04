package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.service.WechatHouseKeeperDepartmentService;
import com.hdedu.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tianjian
 * @className KeeperHouseController
 * @description TODO
 * @date 2021/10/25 16:38
 */
@Api(tags = "微管家部门信息同步")
@RestController
@RequestMapping("/housekeeper")
public class WechatKeeperHouseDepartmentController {

    @Autowired
    private WechatHouseKeeperDepartmentService wechatHouseKeeperDepartmentService;

    @ApiOperation("获取微管家部门信息并作存储")
    @GetMapping("/getDepartmentInfoAndSave")
    public ResultVO getDepartmentInfoAndSave(String str){
        wechatHouseKeeperDepartmentService.getWechatHouseKeeperDepartmentInfoAndSave(str);
        return ResultVO.success();
    }
}