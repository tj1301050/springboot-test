package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className UserEntity
 * @description TODO
 * @date 2021/10/27 9:14
 */
@Data
@TableName("wechat_housekeeper_user")
public class WechatHouseKeeperUserEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("小助手在微管家中的user_id")
    private String wechatHouseKeeperUserId;


    @ApiModelProperty("小助手在微管家中的用户账号")
    private String userAccount;

    @ApiModelProperty("小助手在微管家中的用户名")
    private String userName;

    @ApiModelProperty("小助手在微管家中的昵称")
    private String nickName;

    @ApiModelProperty("小助手在微管家中的部门名称")
    private String userDepartName;

    @ApiModelProperty("是否是客服")
    private String isCustomerService;

    @ApiModelProperty("小助手添加人")
    private String creator;

    @ApiModelProperty("小助手创建时间")
    private String recordCreatedTime;

    @ApiModelProperty("用户状态")
    private String userStatus;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("小助手在微管家中的user_id")
    private Date updateTime;
}