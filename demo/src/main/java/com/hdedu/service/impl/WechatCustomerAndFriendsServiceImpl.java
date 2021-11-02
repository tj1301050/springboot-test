package com.hdedu.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdedu.entity.WechatCustomerAndFriendsEntity;
import com.hdedu.mapper.WechatCustomerAndFriendsMapper;
import com.hdedu.service.WechatCustomerAndFriendsService;
import com.hdedu.test.HttpClientUtils;
import com.hdedu.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tianjian
 * @className WechatCustomerAndFriendsServiceImpl
 * @description TODO
 * @date 2021/10/29 12:03
 */
@Slf4j
@Service
public class WechatCustomerAndFriendsServiceImpl implements WechatCustomerAndFriendsService {

    String appKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjkCBrgH4r5vCFjjw4rl8e325QAaQwfrIaxHe5g/Bk9sxG/JJQW+SqbEl/gwsoqF2C4caE3DtSfFAJuC4OCkyHg==";
    String type = "1";
    String appSecret = "ME0CAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEMzAxAgEBBCDiQaFEbG16suTjRcPtv9IiM3bE7JHbs2srgv3WcB7BFqAKBggqhkjOPQMBBw==";
    String appId = "1352467694234243072";
    String accessCodeUrl = "https://openapi.siycrm.com/api/wechat/Token/GetAccessCode";
    String accessTokenUrl = "https://openapi.siycrm.com/api/wechat/Token/GetAccessToken";


    String wechatFriendsListUrl = "https://openapi.siycrm.com/api/wechat/WechatFriend/List";
    String addWechatFriendsListUrl = "https://openapi.siycrm.com/api/wechat/WechatFriend/IncrementList";
    private String successCode = "100";

    @Autowired
    private WechatCustomerAndFriendsMapper mapper;

    /**
     * 查询好友列表
     */
    @Override
    public void batchInsertWechatCustomerAndFriendsInfo() {
        int pageNumber = 1;
        int pageSize = 100;
        int totalCount = getWechatCustomerAndFriendsInfo(pageNumber, pageSize);
        int pages = totalCount / pageSize;
        int remainder = totalCount % pageSize;
        if (remainder > 0) {
            pages++;
        }
        try {
            for (int i = 0; i < pages; i++) {
                pageNumber++;
                getWechatCustomerAndFriendsInfo(pageNumber, pageSize);
            }
        } catch (Exception e) {
            log.info("这里出错了，当前页是" + pageNumber);
            e.getMessage();
        }
    }


    /**
     * 查询增量好友
     */
    @Override
    public void getAddWechatCustomerAndFriendsInfo() {
        int number = 1;
        int size = 100;
        String lastUpdateTime = mapper.getLastUpdateTime();
        int totalCount = getAddWechatCustomerAndFriendsInfo(number, size, lastUpdateTime);
        int pages = totalCount / size;
        int remainder = totalCount % size;
        if (remainder > 0) {
            pages++;
        }
        try {
            for (int i = 0; i < pages; i++) {
                number++;
                getAddWechatCustomerAndFriendsInfo(number, size, lastUpdateTime);
            }
        } catch (Exception e) {
            log.info("这里出错了，当前页是" + number);
            e.getMessage();
        }
    }

    /**
     * 同步未分配客服的线索
     */
    @Override
    public void getLastWechatCustomerAndFriendsInfoOnTime() {
        List<Integer> friendRecordIds = mapper.getLastWechatCustomerAndFriendsInfoOnTime();
        List<Integer> requestFriendRecordIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(friendRecordIds)) {
            for (int i = 0; i < friendRecordIds.size(); i++) {
                requestFriendRecordIds.add(friendRecordIds.get(i));
                if (requestFriendRecordIds.size() >= 100) {
                    int pageNumber = 1;
                    int pageSize = 100;
                    JSONObject paramObject = new JSONObject();
                    paramObject.put("page_index", pageNumber);
                    paramObject.put("page_size", pageSize);
                    paramObject.put("friends_ids", requestFriendRecordIds);
                    JSONObject jsonObject = getAccessToken();
                    jsonObject = checkTokenAndRefresh(jsonObject);
                    String accessToken = jsonObject.getString("accessToken");
                    StringBuffer friendsBuffer = new StringBuffer();
                    String currentTimeStamp = getSecondTimestamp();
                    String requestStr = friendsBuffer.append(paramObject.toString()).append(currentTimeStamp).append(appSecret).toString();
                    String requestSignature = MD5Utils.MD5(requestStr);
                    String context = HttpClientUtils.doPostBody(wechatFriendsListUrl, paramObject.toString(), currentTimeStamp, requestSignature, accessToken);
                    if (StringUtils.isNotBlank(context)) {
                        JSONObject object = JSONObject.parseObject(context);
                        if (null != object && successCode.equals(object.get("result_code"))) ;
                        JSONObject resultObject = object.getJSONObject("result_object");
                        JSONArray results = resultObject.getJSONArray("results");
                        List<WechatCustomerAndFriendsEntity> list = JSONObject.parseArray(results.toString(), WechatCustomerAndFriendsEntity.class);
                        mapper.updateWechatCustomerAndFriends(list);
                        requestFriendRecordIds.clear();
                    }
                }
            }
        }
    }


    public int getWechatCustomerAndFriendsInfo(Integer pageNumber, Integer pageSize) {
        int totalCount = 0;
        JSONObject paramObject = new JSONObject();
        paramObject.put("page_index", pageNumber);
        paramObject.put("page_size", pageSize);
        JSONObject jsonObject = getAccessToken();
        jsonObject = checkTokenAndRefresh(jsonObject);
        String accessToken = jsonObject.getString("accessToken");
        StringBuffer friendsBuffer = new StringBuffer();
        String currentTimeStamp = getSecondTimestamp();
        String friendListStr = friendsBuffer.append(paramObject.toString()).append(currentTimeStamp).append(appSecret).toString();
        String friendsSignature = MD5Utils.MD5(friendListStr);
        String context = HttpClientUtils.doPostBody(wechatFriendsListUrl, paramObject.toString(), currentTimeStamp, friendsSignature, accessToken);
        if (StringUtils.isNotBlank(context)) {
            JSONObject object = JSONObject.parseObject(context);
            if (null != object && successCode.equals(object.get("result_code"))) ;
            JSONObject resultObject = object.getJSONObject("result_object");
            JSONArray results = resultObject.getJSONArray("results");
            totalCount = resultObject.getInteger("total");
            List<WechatCustomerAndFriendsEntity> list = JSONObject.parseArray(results.toString(), WechatCustomerAndFriendsEntity.class);
            if (!CollectionUtils.isEmpty(list)) {
                mapper.batchInsertWechatCustomerAndFriendsInfo(list);
            }
        }
        return totalCount;
    }

    /**
     * 调用微管家服务，获取access_token
     *
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
        String accessCodeContext = HttpClientUtils.doPostBody(accessCodeUrl, accessCodeContent.toString(), "", "", "");
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
        String accessTokenContext = HttpClientUtils.doPostBody(accessTokenUrl, accessTokenParams.toString(), "", "", "");
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(accessTokenContext)) {
            JSONObject jsonObject = JSONObject.parseObject(accessTokenContext);
            if (successCode.equals(jsonObject.get("result_code"))) {
                JSONObject resultObject = jsonObject.getJSONObject("result_object");
                String accessToken = resultObject.getString("access_token");
                result.put("accessToken", accessToken);
                result.put("generatorTime", System.currentTimeMillis());
            }
        }
        return result;
    }

    /**
     * 校验accessToken,如果过期就重新获取
     *
     * @param jsonObject accessToken和refreshToken
     */
    public JSONObject checkTokenAndRefresh(JSONObject jsonObject) {
        if (null != jsonObject) {
            Long generatorTime = jsonObject.getLong("generatorTime");
            Long currentTime = System.currentTimeMillis();
            if (0 != generatorTime && null != generatorTime) {
                BigDecimal aa = BigDecimal.valueOf(((currentTime - generatorTime) / 1000 / 60));
                if (aa.compareTo(BigDecimal.valueOf(100)) == 1) {
                    jsonObject = getAccessToken();
                }
            } else {
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

    public static void main(String[] args) {
//        String s = "wechat_id -> v3_020b3826fd03010000000000937d039632553f000000501ea9a3dba12f95f6b60a0536a1adb636071ba4a72e92be7df06e1cc7812362f584f1ef9d00c6cac87ba2fa39612cf8bef8b9046904798907ddf66ff5d01241541d7d2ca6815b7e08dd7f@stranger";
//        System.out.println(s.length());
        WechatCustomerAndFriendsServiceImpl impl = new WechatCustomerAndFriendsServiceImpl();
        impl.getAddWechatCustomerAndFriendsInfo();

    }


    /**
     * 查询增量好友并进行存储
     *
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     */
    public int getAddWechatCustomerAndFriendsInfo(Integer pageNumber, Integer pageSize, String lastUpdateTime) {
        int totalCount = 0;
        if (StringUtils.isNotBlank(lastUpdateTime)) {
            JSONObject paramObject = new JSONObject();
            paramObject.put("page_index", pageNumber);
            paramObject.put("page_size", pageSize);
            paramObject.put("start_time", lastUpdateTime);
            JSONObject jsonObject = getAccessToken();
            jsonObject = checkTokenAndRefresh(jsonObject);
            String accessToken = jsonObject.getString("accessToken");
            StringBuffer addBuffer = new StringBuffer();
            String currentTimeStamp = getSecondTimestamp();
            String addFriendListStr = addBuffer.append(paramObject.toString()).append(currentTimeStamp).append(appSecret).toString();
            String addFriendsSignature = MD5Utils.MD5(addFriendListStr);
            String context = HttpClientUtils.doPostBody(addWechatFriendsListUrl, paramObject.toString(), currentTimeStamp, addFriendsSignature, accessToken);
            if (StringUtils.isNotBlank(context)) {
                JSONObject object = JSONObject.parseObject(context);
                if (null != object && successCode.equals(object.get("result_code"))) ;
                JSONObject resultObject = object.getJSONObject("result_object");
                JSONArray results = resultObject.getJSONArray("results");
                totalCount = resultObject.getInteger("total");
                List<WechatCustomerAndFriendsEntity> list = JSONObject.parseArray(results.toString(), WechatCustomerAndFriendsEntity.class);
                if (!CollectionUtils.isEmpty(list)) {
                    List<WechatCustomerAndFriendsEntity> insertList = new ArrayList<>();
                    List<WechatCustomerAndFriendsEntity> updateList = new ArrayList<>();
                    for (WechatCustomerAndFriendsEntity we : list) {
                        QueryWrapper<WechatCustomerAndFriendsEntity> wrapper = new QueryWrapper<>();
                        wrapper.eq("wechat_id", we.getWechatId());
                        wrapper.eq("account_id", we.getAccountId());
                        int count = mapper.selectCount(wrapper);
                        if (count > 0) {
                            updateList.add(we);
                        } else {
                            insertList.add(we);
                        }
                    }
                    if (!CollectionUtils.isEmpty(insertList)) {
                        mapper.batchInsertWechatCustomerAndFriendsInfo(insertList);
                    }
                    if (!CollectionUtils.isEmpty(updateList)) {
                        mapper.updateWechatCustomerAndFriends(updateList);
                    }
                }
            }
        }
        return totalCount;
    }

}