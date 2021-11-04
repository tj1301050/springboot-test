package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className WechatCustomentAndFriendsEntity
 * @description TODO
 * @date 2021/10/29 11:51
 */
@Data
@TableName("sas_siycrm_wechat_user_relationship")
public class WechatCustomerAndFriendsEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("朋友wechat_id")
    private String wechatId;

    @ApiModelProperty("朋友微信昵称")
    private String nickName;

    @ApiModelProperty("朋友微信号")
    private String wechatAlias;

    @ApiModelProperty("朋友微信头像url")
    private String avatar;

    @ApiModelProperty("性别,0:未知,1:男,2:女")
    private Integer gender;

    @ApiModelProperty("朋友来自国家")
    private String country;

    @ApiModelProperty("朋友来自省份")
    private String province;

    @ApiModelProperty("朋友来自城市")
    private String city;

    @ApiModelProperty("朋友手机号")
    private String phone;

    @ApiModelProperty("用户给朋友的标签")
    private String labels;

    @ApiModelProperty("系统给用户朋友的标签")
    private String systemTags;

    @ApiModelProperty("朋友是否删除：0-正常，1-删除")
    private String isDeleted;

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty("数据创建时间")
    private Date createdTime;

    @ApiModelProperty("部门id(客服所在的部门id)")
    private Integer departmentId;

    @ApiModelProperty("客服id(客服在微管家中的id)")
    private Integer accountId;

    @ApiModelProperty("微信号记录id")
    private Integer wechatRecordId;

    @ApiModelProperty("好友记录id")
    private Integer friendRecordId;

    @ApiModelProperty("是否已通过好友请求")
    private Integer isPassed;

    @ApiModelProperty("好友备注信息")
    private String remark;

    @ApiModelProperty("拼音首字母")
    private String pyinitial;

    @ApiModelProperty("全拼")
    private String quanpin;

    @ApiModelProperty("好友来源")
    private Integer source;

    @ApiModelProperty("数据在微管家系统的创建时间")
    private Date createTime;

    @ApiModelProperty("好友申请时间")
    private Date applyTime;

    @ApiModelProperty("好友通过时间")
    private Date passTime;

    @ApiModelProperty("好友添加时间")
    private Date addTime;

    @ApiModelProperty("好友删除时间")
    private Date deleteTime;

    @ApiModelProperty("好友状态")
    private Integer friendStatus;
}