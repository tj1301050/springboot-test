package com.hdedu.service.impl;

import com.hdedu.controller.KeeperHouseUserController;
import com.hdedu.entity.WechatFriendsEntity;
import com.hdedu.entity.WechatHouseKeeperUserEntity;
import com.hdedu.mapper.WechatFriendsMapper;
import com.hdedu.service.WechatFriendsService;
import com.hdedu.utils.GetResourceFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianjian
 * @className WechatFriendsServiceImpl
 * @description TODO
 * @date 2021/11/3 9:06
 */

@Service
public class WechatFriendsServiceImpl implements WechatFriendsService {

    @Autowired
    private WechatFriendsMapper mapper;

    @Override
    public void getWechatFriends() {
        String fileName = KeeperHouseUserController.class.getClassLoader().getResource("WechatFriends.html").getPath();
        try {
            List<WechatFriendsEntity> insertList = new ArrayList<>();
            List<WechatFriendsEntity> updateList = new ArrayList<>();
            String htmlStr = GetResourceFileUtils.readFileByLines(fileName);
            Document document = Jsoup.parse(htmlStr);
            Elements bodyElement = document.getElementsByTag("tbody");
            Elements trs = bodyElement.select("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                Elements tds = tr.select("td");
                WechatFriendsEntity wechatFriendsEntity = new WechatFriendsEntity();
                Elements classElements = tds.get(0).getElementsByClass("md-form__checkbox ck_child");
                String personalWechatId = classElements.attr("value");
                Elements avatarElement = tds.get(1).getElementsByClass("md-table__avatar");
                String avatar = avatarElement.attr("src");
                Elements div = tds.get(1).getElementsByClass("d-flex flex-column justify-content-between ml-2");
                Elements spans = div.select("span");
                String nickName = subStr(spans.get(0).text(), ":");
                String wechatAlias = subStr(spans.get(2).text(), ":");
                String gender = subStr(spans.get(3).text(), ":");
                String wechatVersion = subStr(spans.get(4).text(), ":");
                String status = subStr(spans.get(5).text(), ":");
                String phone = tds.get(2).text();
                Elements friendPElements = tds.get(3).select("p");
                String friendsMan = friendPElements.get(0).select("span").get(1).text();
                String friendsWoman = friendPElements.get(1).select("span").get(1).text();
                String friendsUnknow = friendPElements.get(2).select("span").get(1).text();
                String friendsTotal = friendPElements.get(3).select("span").get(1).text();
                Elements dialoguePElements = tds.get(4).select("p");
                String yesterdayDialogue = dialoguePElements.get(0).select("span").get(1).text();
                String last7daysDialogue = dialoguePElements.get(1).select("span").get(1).text();
                String last30daysDialogue = dialoguePElements.get(2).select("span").get(1).text();
                Elements equipmentPElements = tds.get(5).select("p");
                String custodian = null;
                String userAccount = null;
                String phoneStatus = null;
                String customerStatus = null;
                Elements custodianSpan = equipmentPElements.get(2).select("span");
                Elements phoneStatusSpan = equipmentPElements.get(3).select("span");
                Elements customerStatusSpan = equipmentPElements.get(4).select("span");
                if (custodianSpan.size() > 1) {
                    custodian = equipmentPElements.get(2).select("span").get(1).text();
                    userAccount = subStr(equipmentPElements.get(2).select("span").get(1).text(), "(");
                }
                if (phoneStatusSpan.size() > 1) {
                    phoneStatus = equipmentPElements.get(3).select("span").get(1).text();
                }
                if (customerStatusSpan.size() > 1) {
                    customerStatus = equipmentPElements.get(4).select("span").get(1).text();
                }
                String belongGroup = tds.get(7).select("a").text();
                wechatFriendsEntity.setPersonalWechatId(Integer.parseInt(personalWechatId));
                wechatFriendsEntity.setNickName(nickName);
                wechatFriendsEntity.setWechatAlias(wechatAlias);
                wechatFriendsEntity.setAvatar(avatar);
                wechatFriendsEntity.setGender(gender);
                wechatFriendsEntity.setWechatVersion(wechatVersion);
                wechatFriendsEntity.setCurrentUserStatus(status);
                wechatFriendsEntity.setFriendsTotal(Integer.parseInt(friendsTotal));
                wechatFriendsEntity.setFriendsMan(Integer.parseInt(friendsMan));
                wechatFriendsEntity.setFriendsWoman(Integer.parseInt(friendsWoman));
                wechatFriendsEntity.setFriendsUnknow(Integer.parseInt(friendsUnknow));
                wechatFriendsEntity.setYesterdayDialogue(Integer.parseInt(yesterdayDialogue));
                wechatFriendsEntity.setLast7daysDialogue(Integer.parseInt(last7daysDialogue));
                wechatFriendsEntity.setLast30daysDialogue(Integer.parseInt(last30daysDialogue));
                wechatFriendsEntity.setUserAccount(userAccount);
                wechatFriendsEntity.setCustodian(custodian);
                wechatFriendsEntity.setBelongGroup(belongGroup);
                wechatFriendsEntity.setPhoneStatus(phoneStatus);
                wechatFriendsEntity.setCustomerStatus(customerStatus);
                wechatFriendsEntity.setPhone(phone);
                int count = mapper.selectCountByPersonalWechatId(wechatFriendsEntity.getPersonalWechatId());
                if (count > 0) {
                    updateList.add(wechatFriendsEntity);
                } else {
                    insertList.add(wechatFriendsEntity);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                mapper.batchUpdateWechatFriends(updateList);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                mapper.bathInsertWechatFriends(insertList);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 对爬取的数据进行截取
     *
     * @param str
     * @return
     */
    private static String subStr(String str, String rule) {
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(str.indexOf(rule) + 1);
            return str;
        }
        return null;
    }

}