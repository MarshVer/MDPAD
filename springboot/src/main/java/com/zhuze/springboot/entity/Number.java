package com.zhuze.springboot.entity;

import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-05-04
 */
@Getter
@Setter
@ApiModel(value = "Number对象", description = "")
public class Number implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @Alias("编号")
    @TableId(value = "number_id", type = IdType.AUTO)
    private Integer numberId;

      @ApiModelProperty("智能柜编号")
    @Alias("智能柜编号")
        private Integer cabinetId;

      @ApiModelProperty("智能柜柜号")
    @Alias("智能柜柜号")
        private Integer cabinetNumber;

      @ApiModelProperty("订单号")
    @Alias("订单号")
      private String orderNumber;

      @ApiModelProperty("是否可以工作")
    @Alias("是否可以工作")
      private Boolean numberIsWork;

      @ApiModelProperty("是否为空")
    @Alias("是否为空")
      private Boolean numberIsEmpty;


}
