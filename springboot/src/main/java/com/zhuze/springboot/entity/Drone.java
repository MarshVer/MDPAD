package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import cn.hutool.core.annotation.Alias;
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
 * @since 2023-05-04
 */
@Getter
@Setter
  @ApiModel(value = "Drone对象", description = "")
@ToString
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "drone_id", type = IdType.AUTO)
      private Integer droneId;

      @ApiModelProperty("型号")
    @Alias("型号")
      private String droneModel;

      @ApiModelProperty("自重")
    @Alias("自量")
      private Integer droneWeight;

      @ApiModelProperty("最大载重")
    @Alias("最大载重")
      private Integer maxLoad;

      @ApiModelProperty("剩余续航时间")
    @Alias("剩余续航时间")
      private Time remainEndurance;

      @ApiModelProperty("最大续航时间")
    @Alias("总续航时间")
      private Time maxEndurance;

      @ApiModelProperty("工作状态")
    @Alias("工作状态")
      private Integer workingFlag;

      @ApiModelProperty("是否可以工作")
    @Alias("是否可以工作")
      private Boolean droneIsWork;

      @ApiModelProperty("生产日期")
    @Alias("生产日期")
      private LocalDate droneManufacturingDate;


}
