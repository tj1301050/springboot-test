package com.hdedu.param.wechathousekeeper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @author tianjian
 * @className QueryUserFriendsParam
 * @description TODO
 * @date 2021/10/28 15:19
 */
@Data
public class QueryUserFriendsParam {

    @JsonProperty("wechat_record_id")
    @ApiModelProperty("微信号记录ID（是工作微信在数据表的主键ID")
    private Integer wechatRecordId;

    @JsonProperty("friends_id")
    @ApiModelProperty("好友记录id（是好友在数据表的主键ID（用来标识工作微信与微信好友的关系））")
    private Integer friendId;

    @JsonProperty("wechat_id")
    @ApiModelProperty("微信原始ID")
    private String wechatId;

    @JsonProperty("nickname")
    @ApiModelProperty("昵称 ")
    private String nickName;

    @JsonProperty("friends_ids")
    @ApiModelProperty("好友记录ID列表")
    private List<Integer> friendsIds;

    @JsonProperty("ispass")
    @ApiModelProperty("是否通过, 1是 0否")
    private Integer isPass;

    @JsonProperty("isdeleted")
    @ApiModelProperty("是否删除, 1是0否")
    private Integer isDeleted;

    @JsonProperty("gender")
    @ApiModelProperty("性别,0:未知,1:男,2:女")
    private Integer gender;

    @JsonProperty("start_time")
    @ApiModelProperty("开始时间,对应好友列表的last_update_time字段,时间格式:\"yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @JsonProperty("remark")
    @ApiModelProperty("好友备注信息")
    private String remark;

    @JsonProperty("wechat_alias")
    @ApiModelProperty("好友微信号")
    private String wechatAlias;

    @JsonProperty("start_friends_id")
    @ApiModelProperty("起始好友记录ID")
    private Integer startFriendsId;

    @JsonProperty("phone")
    @ApiModelProperty("电话号码")
    private String phone;

    @JsonProperty("page_index")
    @ApiModelProperty("页码,默认从1开始")
    private Integer pageIndex = 1;

    @JsonProperty("page_size")
    @ApiModelProperty("分页大小，默认返回每页20条(最大每页100条)。")
    private Integer pageSize = 100;
}