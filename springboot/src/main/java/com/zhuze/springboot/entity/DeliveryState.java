package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
  @TableName("delivery_state")
@ApiModel(value = "DeliveryState对象", description = "")
@ToString
public class DeliveryState implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "delivery_state_id", type = IdType.AUTO)
      private Integer deliveryStateId;

      @ApiModelProperty("配送状态")
    @Alias("配送状态")
      private String deliveryState;

      @ApiModelProperty("唯一标识")
    @Alias("唯一标识")
      private Integer deliveryFlag;


}
