package com.hdedu.controller;

import com.hdedu.base.ResultVO;
import com.hdedu.mapper.WechatHouseKeeperDepartmentMapper;
import com.hdedu.service.WechatHouseKeeperDepartmentService;
import com.hdedu.test.HttpClientUtils;
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

    @ApiOperation("获取图片验证码")
    @GetMapping("/getVerification")
    public ResultVO getVerificationCode(HttpServletRequest request) throws IOException {
        // 生成验证码前的字符串
        String url1 = "https://login.sso.siycrm.com/Authorize/GetImgVerifyCodeKey";

        // 生成验证码
        String url2 = "https://login.sso.siycrm.com/Authorize/GetImgVerifyCode";
        String firstContext = HttpClientUtils.HttpGetWithOutParam(url1);
        String verificationCode = null;
        if (StringUtils.isNotBlank(firstContext)) {
            List<NameValuePair> nameValuePairList = new LinkedList<>();
            BasicNameValuePair param1 = new BasicNameValuePair("key", firstContext);
            nameValuePairList.add(param1);
            verificationCode = HttpClientUtils.HttpGetWithParam(nameValuePairList, url2, request);
            FileUtils.writeStringToFile(new File("E:\\picture\\ocrImg\\1.jpg"), verificationCode, "UTF-8");
            System.out.println("-------------");
            System.out.println("verificationCode");
            System.out.println(verificationCode);
        }
        return ResultVO.success(verificationCode);
    }

//    @ApiOperation("从微管家获取组织架构信息")
//    @GetMapping("/getDepartmentInfoFromHouseKeeper")
//    public ResultVO getDepartmentInfoFromHouseKeeper(HttpServletRequest request) throws IOException {
//        String url = "https://apollo.siycrm.com/Merchant/Department/DepartmentTreeData";
//        List<NameValuePair> list = new LinkedList<>();
//        BasicNameValuePair nameValuePair = new BasicNameValuePair("id", "1");
//        list.add(nameValuePair);
//        String context = HttpClientUtils.HttpGetWithParam(list, url, request);
//        System.out.println(context);
//        return ResultVO.success(context);
//    }

    @ApiOperation("登录前获Referer")
    @GetMapping("/getRefererBeforeLogin")
    public ResultVO beforeLogin(HttpServletRequest request) {
        String url = "https://login.sso.siycrm.com/Authorize/GetLoginVerifCode";
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair nameValuePair = new BasicNameValuePair("userAccount", "田俭123");
        BasicNameValuePair clientIdValuePair = new BasicNameValuePair("clientId", "81c4e66b04066aa8da079f3e5d2bf1f3");
        list.add(nameValuePair);
        list.add(clientIdValuePair);
        String context = "";
        try {
            context = HttpClientUtils.HttpGetWithParam(list, url, request);
            System.out.println("----------------------==" + context);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return ResultVO.success();
    }

    public static String readFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = bufferedReader.readLine();
                while (lineTxt != null) {
                    return lineTxt;
                }
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.out.println("Cannot find the file specified!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file content!");
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("获取微管家部门信息并作存储")
    @GetMapping("/getDepartmentInfoAndSave")
    public ResultVO getDepartmentInfoAndSave(){
        wechatHouseKeeperDepartmentService.getWechatHouseKeeperDepartmentInfoAndSave();
        return ResultVO.success();
    }
}