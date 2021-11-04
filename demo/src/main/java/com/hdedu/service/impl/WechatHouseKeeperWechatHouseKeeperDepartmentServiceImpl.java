package com.hdedu.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdedu.controller.WechatKeeperHouseDepartmentController;
import com.hdedu.entity.WechatHouseKeeperDepartmentEntity;
import com.hdedu.mapper.WechatHouseKeeperDepartmentMapper;
import com.hdedu.service.WechatHouseKeeperDepartmentService;
import com.hdedu.utils.GetResourceFileUtils;
import com.hdedu.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author tianjian
 * @className DepartmentServiceImpl
 * @description TODO
 * @date 2021/10/26 15:19
 */
@Service
public class WechatHouseKeeperWechatHouseKeeperDepartmentServiceImpl implements WechatHouseKeeperDepartmentService {

    String departmentInfoUrl = "https://apollo.siycrm.com/Merchant/Department/DepartmentTreeData?id=1";

    @Autowired
    private WechatHouseKeeperDepartmentMapper wechatHouseKeeperDepartmentMapper;

    @Override
    public void getWechatHouseKeeperDepartmentInfoAndSave(String str) {
//        String fileName = WechatKeeperHouseDepartmentController.class.getClassLoader().getResource("departmentInfo.txt").getPath();
        try {
//            String departmentStr = GetResourceFileUtils.readFileByLines(fileName);
            Map<String,String> map = new HashMap<>();
            String departmentStr = HttpClientUtils.doGet(departmentInfoUrl,map,str);
            System.out.println(departmentStr);
            JSONArray jsonArray = JSONArray.parseArray(departmentStr);
            List<WechatHouseKeeperDepartmentEntity> wechatHouseKeeperDepartmentEntityList = new ArrayList<>();
            if (null != jsonArray && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    WechatHouseKeeperDepartmentEntity wechatHouseKeeperDepartmentEntity = new WechatHouseKeeperDepartmentEntity();
                    wechatHouseKeeperDepartmentEntity.setDepartmentId(object.getString("id"));
                    wechatHouseKeeperDepartmentEntity.setParentDepartmentId(object.getString("parent"));
                    wechatHouseKeeperDepartmentEntity.setDepartmentName(object.getString("text"));
                    wechatHouseKeeperDepartmentEntityList.add(wechatHouseKeeperDepartmentEntity);
                }
            }
            List<WechatHouseKeeperDepartmentEntity> insertList = new ArrayList<>();
            List<WechatHouseKeeperDepartmentEntity> updateList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(wechatHouseKeeperDepartmentEntityList)) {
                for (WechatHouseKeeperDepartmentEntity wechatHouseKeeperDepartmentEntity : wechatHouseKeeperDepartmentEntityList) {
                    int count = wechatHouseKeeperDepartmentMapper.selectCountByDepartmentId(wechatHouseKeeperDepartmentEntity.getDepartmentId());
                    if (count > 0) {
                        updateList.add(wechatHouseKeeperDepartmentEntity);
                    } else {
                        insertList.add(wechatHouseKeeperDepartmentEntity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                wechatHouseKeeperDepartmentMapper.bathInsertDepartment(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (int i = 0; i < updateList.size(); i++) {
                    WechatHouseKeeperDepartmentEntity department = updateList.get(i);
                    QueryWrapper<WechatHouseKeeperDepartmentEntity> wrapper = new QueryWrapper<>();
                    wrapper.eq("department_id", department.getDepartmentId());
                    department.setUpdateTime(new Date());
                    wechatHouseKeeperDepartmentMapper.update(department, wrapper);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}