package com.zhuze.springboot.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2023-04-30
 */
@Getter
@Setter
@ApiModel(value = "Staff对象", description = "")
@ToString
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @Alias("编号")
    @TableId(value = "staff_id", type = IdType.AUTO)
    private Integer staffId;

    @ApiModelProperty("姓名")
    @Alias("姓名")
    private String staffName;

    @ApiModelProperty("用户名")
    @Alias("用户名")
    private String staffUsername;

    @ApiModelProperty("密码")
    @Alias("密码")
    private String staffPassword;

    @ApiModelProperty("性别")
    @Alias("性别")
    private Boolean staffGender;

    @ApiModelProperty("电话")
    @Alias("电话")
    private String staffPhone;

    @ApiModelProperty("邮箱")
    @Alias("邮箱")
    private String staffEmail;

    @ApiModelProperty("身份证号")
    @Alias("身份证号")
    private String idCard;

    @ApiModelProperty("职位")
    @Alias("职位")
    private Integer position;

    @ApiModelProperty("出生日期")
    @Alias("出生日期")
    private LocalDate staffBirthTime;

    @ApiModelProperty("进入单位时间")
    @Alias("进入单位时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime enterTime;

}
