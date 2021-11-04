package com.hdedu.service.impl;

import com.hdedu.entity.UserUnderTagsEntity;
import com.hdedu.entity.WechatUserTags;
import com.hdedu.mapper.UserUnderTagsMapper;
import com.hdedu.mapper.WechatFriendsMapper;
import com.hdedu.mapper.WechatUserTagsMapper;
import com.hdedu.service.WechatFriendsAndTagsService;
import com.hdedu.utils.HttpClientUtils;
import com.hdedu.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tianjian
 * @className WechatFriendsAndTagsServiceImpl
 * @description TODO
 * @date 2021/11/3 14:57
 */
@Service
public class WechatFriendsAndTagsServiceImpl implements WechatFriendsAndTagsService {

    String baseWechatAndTagsUrl = "https://apollo.siycrm.com/Weixin/Tag/FriendsTagList";

    String baseTagsAndFriendsUrl = "https://apollo.siycrm.com/WeiXin/Tag/FriendsCover";
    String personal_wechat_id = "personal_wechat_id=";
    String page_size = "&pageSize=200";

    @Autowired
    private WechatFriendsMapper wechatFriendsMapper;

    @Autowired
    private UserUnderTagsMapper userUnderTagsMapper;

    @Autowired
    private WechatUserTagsMapper wechatUserTagsMapper;

    @Override
    public void batchInsertTags(String str) {
        List<Integer> personalWechatIds = wechatFriendsMapper.getWechatId();
        if (!CollectionUtils.isEmpty(personalWechatIds)) {
            for (Integer personalWechatId : personalWechatIds) {
                StringBuffer wechatAndTagsBuffer = new StringBuffer();
                String firstUrl = wechatAndTagsBuffer.append(baseWechatAndTagsUrl).append("?").append(personal_wechat_id).append(personalWechatId).append(page_size).toString();
                int totalPage = saveWechatTagsAndUsersUnderTags(firstUrl, str, personalWechatId);
                for (int i = 1; i < totalPage; i++) {
                    StringBuffer secondBuffer = new StringBuffer();
                    String secondUrl = secondBuffer.append(baseWechatAndTagsUrl).append("/").append(i + 1).append("?").append(personal_wechat_id).append(personalWechatId).append(page_size).toString();
                    saveWechatTagsAndUsersUnderTags(secondUrl, str, personalWechatId);
                }
            }
        }

    }


    private int saveWechatTagsAndUsersUnderTags(String url, String str, Integer personalWechatId) {
        int total = 1;
        Map<String, String> map = new HashMap<>();
        try {
            List<WechatUserTags> wechatUserTagsList = new ArrayList<>();
            String htmlStr = HttpClientUtils.doGet(url, map, str);
            Document document = Jsoup.parse(htmlStr);
            Elements bodyElement = document.getElementsByTag("tbody");
            Elements trs = bodyElement.select("tr");
            for (int i = 0; i < trs.size(); i++) {
                WechatUserTags wechatUserTags = new WechatUserTags();
                Element tr = trs.get(i);
                Elements tds = tr.select("td");
                Elements classElements = tds.get(0).getElementsByClass("md-form__checkbox ck_child");
                String tagId = classElements.attr("value");
                String tagName = tds.get(1).select("span").text();
                String tagFriendsNumber = tds.get(2).select("a").text();
                String totalPage = StringUtils.strSub(document.getElementsByClass("md-table--pagination-num").text());
                total = Integer.parseInt(totalPage);
                wechatUserTags.setPersonalWechatId(personalWechatId);
                wechatUserTags.setTagFriendsNumber(Integer.parseInt(tagFriendsNumber));
                wechatUserTags.setTagId(Integer.parseInt(tagId));
                wechatUserTags.setTagName(tagName);
                StringBuffer tagsAndFriendsUrlBuffer = new StringBuffer();
                String tagsAndFriendsUrl = tagsAndFriendsUrlBuffer.append(baseTagsAndFriendsUrl).append("?id=").append(tagId).toString();
                String tagFriendsHtml = HttpClientUtils.doGet(tagsAndFriendsUrl, map, str);
                Document tagFriendDocument = Jsoup.parse(tagFriendsHtml);
                Elements tagFriendBodyElement = tagFriendDocument.getElementsByClass("col-sm-4");
                List<UserUnderTagsEntity> userUnderTagsEntities = new ArrayList<>();
                for (int j = 2; j < tagFriendBodyElement.size(); j++) {
                    UserUnderTagsEntity userUnderTagsEntity = new UserUnderTagsEntity();
                    Element divs = tagFriendBodyElement.get(j);
                    String avatar = divs.getElementsByTag("img").attr("src");
                    String tagUserName = divs.select("span").text();
                    userUnderTagsEntity.setAvatar(avatar);
                    userUnderTagsEntity.setTagUserName(tagUserName);
                    userUnderTagsEntity.setTagId(Integer.parseInt(tagId));
                    userUnderTagsEntities.add(userUnderTagsEntity);
                }
                if (!CollectionUtils.isEmpty(userUnderTagsEntities)) {
                    userUnderTagsMapper.batchInsertUserUnderTags(userUnderTagsEntities);
                }
                wechatUserTagsList.add(wechatUserTags);
            }
            if (!CollectionUtils.isEmpty(wechatUserTagsList)) {
                wechatUserTagsMapper.batchInsertWechatUserTags(wechatUserTagsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }



    public static void main(String[] args) {
        WechatFriendsAndTagsServiceImpl impl = new WechatFriendsAndTagsServiceImpl();
        impl.batchInsertTags("ApolloUser=U%2554ID%3DC66EB8FB89A67523%26U%2554UserAccount%3DF06F08B7B874A8B1DFC0FBB3ED1FC219%26U%2554TName%3D135CCFB6C5DBC7CE%26U%2554MerchantID%3DCAC3994849F88155; U%2554Token=E56F2D8BC561AFA71C37DF5063BBF8CE966F23C6E5C343C090186D0A64F7219F787D0F4438B31D408B10209A08B3503A");

    }
}