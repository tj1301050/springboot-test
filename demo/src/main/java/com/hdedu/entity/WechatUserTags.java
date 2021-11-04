package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className WechatUserTags
 * @description TODO
 * @date 2021/11/3 15:46
 */
@Data
@TableName("sas_siycrm_wechat_user_tag")
public class WechatUserTags {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("私人id")
    private Integer personalWechatId;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签")
    private Integer tagId;

    @ApiModelProperty("该标签对应的好友数")
    private Integer tagFriendsNumber;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}