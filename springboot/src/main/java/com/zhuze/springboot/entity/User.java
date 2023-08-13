package com.zhuze.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
 * @since 2023-04-29
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @Alias("用户编号")
      @ApiModelProperty("id")
      @TableId(value = "user_id", type = IdType.AUTO)
      private Integer userId;

      @Alias("用户名")
      @ApiModelProperty("用户名")
      private String userUsername;

      @Alias("密码")
      @ApiModelProperty("密码")
      private String userPassword;

      @Alias("昵称")
      @ApiModelProperty("昵称")
      private String nickname;

      @Alias("性别")
      @ApiModelProperty("性别")
      private Boolean userGender;

      @Alias("邮箱")
      @ApiModelProperty("邮箱")
      private String userEmail;

      @Alias("电话")
      @ApiModelProperty("电话")
      private String userPhone;

      @Alias("出生日期")
      @ApiModelProperty("出生日期")
      private LocalDate userBirthTime;

      @Alias("创建时间")
      @ApiModelProperty("创建时间")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private Date userCreateTime;

}
