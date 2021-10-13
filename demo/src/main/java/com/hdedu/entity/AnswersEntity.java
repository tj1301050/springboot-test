package com.hdedu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("问卷答案")
@TableName("answers")
public class AnswersEntity {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("问卷id")
    private Long questionnaireId;

    @ApiModelProperty("实际奖励发放金额")
    private BigDecimal rewardTotal;

    @ApiModelProperty("是否隐藏：0=隐藏，1=显示")
    private Integer status;

    @ApiModelProperty("公开课id")
    private Long tutorialClassId;

    @ApiModelProperty("公开课时间段id")
    private Long tutorialClassTimeId;

    @ApiModelProperty("是否过滤")
    private Integer isFilter;
}
