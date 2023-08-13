package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-05-09
 */
@Getter
@Setter
  @TableName("order_")
@ApiModel(value = "Order对象", description = "")
@ToString
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "order_id", type = IdType.AUTO)
      private Integer orderId;

      @ApiModelProperty("订单号")
    @Alias("订单号")
      private String orderNumber;

      @ApiModelProperty("发货人")
    @Alias("发货人")
      private String consignor;

      @ApiModelProperty("收货人")
    @Alias("收货人")
      private String consignee;

      @ApiModelProperty("发货人地址")
    @Alias("发货人地址")
      private String consignorAddress;

      @ApiModelProperty("收货人地址")
    @Alias("收货人地址")
      private String consigneeAddress;

      @ApiModelProperty("发货人电话")
    @Alias("发货人电话")
      private String consignorPhone;

      @ApiModelProperty("收货人电话")
    @Alias("收货人电话")
      private String consigneePhone;

      @ApiModelProperty("订单状态")
    @Alias("订单状态")
      private Integer orderFlag;

      @ApiModelProperty("开单日期")
    @Alias("开单日期")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private Date billingDate;

      @ApiModelProperty("结单日期")
    @Alias("结单日期")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private LocalDateTime endDate;

}
