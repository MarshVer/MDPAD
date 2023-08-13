package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
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
 * @since 2023-05-12
 */
@Getter
@Setter
  @ApiModel(value = "Delivery对象", description = "")
@ToString
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "delivery_id", type = IdType.AUTO)
      private Integer deliveryId;

      @ApiModelProperty("订单号")
    @Alias("订单号")
      private String orderNumber;

      @ApiModelProperty("无人机编号")
    @Alias("无人机编号")
      private Integer droneId;

      @ApiModelProperty("智能柜编号")
    @Alias("智能柜编号")
      private Integer cabinetId;

      @ApiModelProperty("智能柜柜号")
    @Alias("智能柜柜号")
      private Integer cabinetNumber;

      @ApiModelProperty("配送状态")
    @Alias("配送状态")
      private Integer deliveryFlag;

      @ApiModelProperty("开始时间")
    @Alias("开始时间")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private LocalDateTime startDate;

      @ApiModelProperty("结束时间")
    @Alias("结束时间")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private LocalDateTime endDate;


}
