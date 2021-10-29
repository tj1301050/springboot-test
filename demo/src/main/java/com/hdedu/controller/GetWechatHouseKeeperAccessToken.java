package com.hdedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hdedu.param.wechathousekeeper.QueryUserFriendsParam;
import com.hdedu.test.HttpClientUtils;
import com.hdedu.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;


import java.math.BigDecimal;
import java.util.*;

/**
 * @author tianjian
 * @className GetWechatHouseKeeperAccessToken
 * @description TODO
 * @date 2021/10/28 9:22
 */
//@Api(tags = "对接微管家")
//@RestController
//@RequestMapping("/wechat/houseKeeper")
public class GetWechatHouseKeeperAccessToken {

    String appKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjkCBrgH4r5vCFjjw4rl8e325QAaQwfrIaxHe5g/Bk9sxG/JJQW+SqbEl/gwsoqF2C4caE3DtSfFAJuC4OCkyHg==";
    String type = "1";
    String appSecret = "ME0CAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEMzAxAgEBBCDiQaFEbG16suTjRcPtv9IiM3bE7JHbs2srgv3WcB7BFqAKBggqhkjOPQMBBw==";
    String appId = "1352467694234243072";
    String accessCodeUrl = "https://openapi.siycrm.com/api/wechat/Token/GetAccessCode";
    String accessTokenUrl = "https://openapi.siycrm.com/api/wechat/Token/GetAccessToken";


    String wechatFriendsListUrl = "https://openapi.siycrm.com/api/wechat/WechatFriend/List";
    String addWechatFriendsListUrl = "https://openapi.siycrm.com/api/wechat/WechatFriend/IncrementList";
    private String successCode = "100";

    /**
     * 调用微管家服务，获取access_token
     * @return
     */
    public JSONObject getAccessToken() {
        StringBuffer accessCodeBuffer = new StringBuffer();
        String accessCodeStr = accessCodeBuffer.append(appKey).append("%%").append(type).append("%%").append(appSecret).append("%%").append(getSecondTimestamp()).toString();
        String accessCodeSignature = MD5Utils.MD5(accessCodeStr);
        JSONObject accessCodeContent = new JSONObject();
        accessCodeContent.put("app_id", appId);
        accessCodeContent.put("app_key", appKey);
        accessCodeContent.put("type", type);
        accessCodeContent.put("timestamp", getSecondTimestamp());
        accessCodeContent.put("signature", accessCodeSignature);
        String accessCodeContext = HttpClientUtils.doPostBody(accessCodeUrl, accessCodeContent.toString(),"");
        String accessCode = "";
        if (StringUtils.isNotBlank(accessCodeContext)) {
            JSONObject jsonObject = JSONObject.parseObject(accessCodeContext);
            if (successCode.equals(jsonObject.get("result_code"))) {
                JSONObject resultObject = jsonObject.getJSONObject("result_object");
                accessCode = resultObject.getString("access_code");
            }
        }
        StringBuffer accessTokenBuffer = new StringBuffer();
        String accessTokenStr = accessTokenBuffer.append(appKey).append("%%").append(accessCode).append("%%").append(appSecret).append("%%").append(getSecondTimestamp()).toString();
        String accessTokenSignature = MD5Utils.MD5(accessTokenStr);
        JSONObject accessTokenParams = new JSONObject();
        accessTokenParams.put("app_id", appId);
        accessTokenParams.put("app_key", appKey);
        accessTokenParams.put("code", accessCode);
        accessTokenParams.put("timestamp", getSecondTimestamp());
        accessTokenParams.put("signature", accessTokenSignature);
        String accessTokenContext = HttpClientUtils.doPostBody(accessTokenUrl, accessTokenParams.toString(),"");
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(accessTokenContext)) {
            JSONObject jsonObject = JSONObject.parseObject(accessTokenContext);
            if (successCode.equals(jsonObject.get("result_code"))) {
                JSONObject resultObject = jsonObject.getJSONObject("result_object");
                String accessToken = resultObject.getString("access_token");
                result.put("accessToken",accessToken);
                result.put("generatorTime",System.currentTimeMillis());
            }
        }
        return result;
    }

    /**
     * 校验accessToken,如果过期就重新获取
     * @param jsonObject accessToken和refreshToken
     */
    public  JSONObject checkTokenAndRefresh(JSONObject jsonObject){
        if (null != jsonObject){
            Long generatorTime = jsonObject.getLong("generatorTime");
            Long currentTime = System.currentTimeMillis();
            BigDecimal aa = BigDecimal.valueOf(((currentTime - generatorTime)/1000/60));
            if (aa.compareTo(BigDecimal.valueOf(100)) == 1){
               jsonObject = getAccessToken();
            }
        }
        return jsonObject;
    }
    /**
     * 获取当前时间的毫秒数
     *
     * @return
     */
    public static String getSecondTimestamp() {
        String timestamp = String.valueOf(new Date().getTime());
        int length = timestamp.length();
        if (length > 3) {
            return timestamp.substring(0, length - 3);
        }
        return "0";
    }

//    @ApiOperation("查询客服的好友")
//    @PostMapping("/get/friends")
    public void getHelperFriends(){
        QueryUserFriendsParam param = new QueryUserFriendsParam();
        JSONObject jsonObject = getAccessToken();
        jsonObject = checkTokenAndRefresh(jsonObject);
        String accessToken = jsonObject.getString("accessToken");
        StringBuffer friendsBuffer = new StringBuffer();
        String currentTimeStamp = getSecondTimestamp();
        String friendListStr = friendsBuffer.append(param).append(currentTimeStamp).append(appSecret).toString();
        String friendsSignature = MD5Utils.MD5(friendListStr);
        JSONObject headerObject = new JSONObject();
        headerObject.put("timestamp",currentTimeStamp);
        headerObject.put("signature",friendsSignature);
        headerObject.put("accessToken",accessToken);
        String context = HttpClientUtils.doPostBody(wechatFriendsListUrl,headerObject.toString(),"");
        System.out.println(currentTimeStamp);
        System.out.println(friendsSignature);
        System.out.println(accessToken);
        System.out.println("===================");
        System.out.println(context);
    }

    public static void main(String[] args) {
        GetWechatHouseKeeperAccessToken getWechatHouseKeeperAccessToken = new GetWechatHouseKeeperAccessToken();
//        System.out.println(getWechatHouseKeeperAccessToken.getAccessToken());;
        getWechatHouseKeeperAccessToken.getHelperFriends();
//        JSONObject jsonObject = new JSONObject();
//        getWechatHouseKeeperAccessToken.checkTokenAndRefresh(jsonObject);
    }
}