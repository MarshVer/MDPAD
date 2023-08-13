package com.zhuze.springboot.entity;

import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-05-07
 */
@Getter
@Setter
  @ApiModel(value = "Role对象", description = "")
@ToString
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
      @TableId(value = "role_id", type = IdType.AUTO)
        private Integer roleId;

      @ApiModelProperty("名称")
    @Alias("名称")
      private String roleName;

      @ApiModelProperty("唯一标识")
    @Alias("唯一标识")
      private Integer flag;


}
