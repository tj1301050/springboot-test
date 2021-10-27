package com.hdedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdedu.controller.KeeperHouseUserController;
import com.hdedu.entity.WechatHouseKeeperUserEntity;
import com.hdedu.mapper.WechatHouseKeeperUserMapper;
import com.hdedu.service.WechatHouseKeeperUserService;
import com.hdedu.utils.GetResourceFileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tianjian
 * @className WechatHouseKeeperUserServiceImpl
 * @description TODO
 * @date 2021/10/27 10:11
 */
@Service
public class WechatHouseKeeperUserServiceImpl implements WechatHouseKeeperUserService {

    @Autowired
    private WechatHouseKeeperUserMapper userMapper;

    /**
     * 1.获取html页面并对数据解析，用集合存储
     * 2.对集合数据进行筛选(新增还是更新)
     */
    @Override
    public void getWechatHouseKeeperUserInfo() {
        String fileName = KeeperHouseUserController.class.getClassLoader().getResource("UserHtml.html").getPath();
        try {
            String htmlStr = GetResourceFileUtils.readFileByLines(fileName);
            Document document = Jsoup.parse(htmlStr);
            Elements bodyElement = document.getElementsByTag("tbody");
            Elements trs = bodyElement.select("tr");
            List<WechatHouseKeeperUserEntity> wechatHouseKeeperUserEntityList = new ArrayList<>();
            List<WechatHouseKeeperUserEntity> insertList = new ArrayList<>();
            List<WechatHouseKeeperUserEntity> updateList = new ArrayList<>();
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                Elements tds = tr.select("td");
                WechatHouseKeeperUserEntity userEntity = new WechatHouseKeeperUserEntity();
                String accountText = tds.get(1).text();
                String nameText = tds.get(2).text();
                String nickText = tds.get(3).text();
                String departmentText = tds.get(4).text();
                String isCustomerServiceText = tds.get(5).text();
                String creatorText = tds.get(6).text();
                String createTimeText = tds.get(7).text();
                String statusText = tds.get(8).text();
                Elements aTags = tds.get(9).select("a");
                String userIdText = aTags.get(0).attr("data-value");
                userEntity.setUserAccount(accountText);
                userEntity.setUserName(nameText);
                userEntity.setNickName(nickText);
                userEntity.setUserDepartName(departmentText);
                userEntity.setIsCustomerService(isCustomerServiceText);
                userEntity.setCreator(creatorText);
                userEntity.setRecordCreatedTime(createTimeText);
                userEntity.setUserStatus(statusText);
                userEntity.setWechatHouseKeeperUserId(userIdText);
                wechatHouseKeeperUserEntityList.add(userEntity);
            }
            if (!CollectionUtils.isEmpty(wechatHouseKeeperUserEntityList)) {
                for (WechatHouseKeeperUserEntity userEntity : wechatHouseKeeperUserEntityList) {
                    int count = userMapper.selectCountByWechatHouseKeeperUserId(userEntity.getWechatHouseKeeperUserId());
                    if (count > 0) {
                        updateList.add(userEntity);
                    }else {
                        insertList.add(userEntity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                userMapper.bathInsertUser(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (WechatHouseKeeperUserEntity userEntity : updateList) {
                    QueryWrapper<WechatHouseKeeperUserEntity> wrapper = new QueryWrapper<>();
                    wrapper.eq("wechat_house_keeper_user_id", userEntity.getWechatHouseKeeperUserId());
                    userEntity.setUpdateTime(new Date());
                    userMapper.update(userEntity, wrapper);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}