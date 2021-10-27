package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author tianjian
 * @className DepartmentEntity
 * @description TODO
 * @date 2021/10/26 14:34
 */
@Data
@TableName("wechat_housekeeper_department")
public class WechatHouseKeeperDepartmentEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("部门id")
    private String departmentId;

    @ApiModelProperty("父部门id")
    private String parentDepartmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("当前时间")
    private Date updateTime;
}