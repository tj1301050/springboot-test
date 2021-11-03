package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className WechatFriendsEntity
 * @description TODO
 * @date 2021/11/3 9:12
 */
@Data
@TableName("wechat_friends")
public class WechatFriendsEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("私人id")
    private Integer personalWechatId;

    @ApiModelProperty("微信昵称")
    private String nickName;

    @ApiModelProperty("微信号")
    private String wechatAlias;

    @ApiModelProperty("头像url")
    private String avatar;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("微信手机号")
    private String phone;

    @ApiModelProperty("微信版本")
    private String wechatVersion;

    @ApiModelProperty("当前用户状态")
    private String currentUserStatus;

    @ApiModelProperty("好友总数")
    private Integer friendsTotal;

    @ApiModelProperty("男性好友总数")
    private Integer friendsMan;

    @ApiModelProperty("女性好友总数")
    private Integer friendsWoman;

    @ApiModelProperty("未知性别的好友数量")
    private Integer friendsUnknow;

    @ApiModelProperty("昨日好友对话统计")
    private Integer yesterdayDialogue;

    @ApiModelProperty("近7日好友对话统计")
    private Integer last7daysDialogue;

    @ApiModelProperty("近30日好友对话统计")
    private Integer last30daysDialogue;

    @ApiModelProperty("用户在微管家中的登录账号")
    private String userAccount;

    @ApiModelProperty("保管人员")
    private String custodian;

    @ApiModelProperty("手机状态")
    private String phoneStatus;

    @ApiModelProperty("客服状态")
    private String customerStatus;

    @ApiModelProperty("所属分组")
    private String belongGroup;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}