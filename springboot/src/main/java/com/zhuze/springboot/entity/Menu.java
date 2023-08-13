package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.agent.builder.AgentBuilder;

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
  @ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @Alias("编号")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

      @ApiModelProperty("父级id")
      @Alias("父级id")
      private Integer pid;

      @ApiModelProperty("名称")
    @Alias("名称")
      private String menuName;

      @ApiModelProperty("路径")
    @Alias("路径")
      private String path;

      @ApiModelProperty("图标")
    @Alias("图标")
      private String icon;

      @TableField(exist = false)
      private List<Menu> children;

      private String pagePath;
}
