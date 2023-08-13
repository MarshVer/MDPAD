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
  @TableName("order_state")
@ApiModel(value = "OrderState对象", description = "")
public class OrderState implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "order_state_id", type = IdType.AUTO)
      private Integer orderStateId;

    private String orderState;

    private Integer orderFlag;


}
