package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className UserUnderTagsEntity
 * @description TODO
 * @date 2021/11/3 16:54
 */
@Data
@TableName("sas_siycrm_user_under_tag")
public class UserUnderTagsEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("标签id")
    private Integer tagId;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("该标签下的用户名")
    private String tagUserName;

    @ApiModelProperty("创建时间")
    private Date createdTime;
}