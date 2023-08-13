package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2023-05-04
 */
@Getter
@Setter
  @ApiModel(value = "Inventory对象", description = "")
@ToString
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "inventory_id", type = IdType.AUTO)
      private Integer inventoryId;

      @ApiModelProperty("订单号")
    @Alias("订单号")
      private String orderNumber;

      @ApiModelProperty("重量")
    @Alias("重量")
      private Integer inventoryWeight;

      @ApiModelProperty("是否损坏")
    @Alias("是否损坏")
      private Boolean isDamage;

      @ApiModelProperty("入库时间")
    @Alias("入库时间")
      @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private LocalDateTime warehousingDate;


}
