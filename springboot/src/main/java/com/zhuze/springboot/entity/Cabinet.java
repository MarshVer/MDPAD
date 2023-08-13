package com.zhuze.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
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
 * @since 2023-05-04
 */
@Getter
@Setter
  @ApiModel(value = "Cabinet对象", description = "")
@ToString
public class Cabinet implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("编号")
    @Alias("编号")
        @TableId(value = "cabinet_id", type = IdType.AUTO)
      private Integer cabinetId;

      @ApiModelProperty("型号")
    @Alias("型号")
      private String cabinetModel;

      @ApiModelProperty("柜数")
    @Alias("柜数")
      private Integer cabinetNumber;

      @ApiModelProperty("智能柜体积")
    @Alias("智能柜体积")
      private String cabinetVolume;

      @ApiModelProperty("单柜尺寸")
    @Alias("单柜尺寸")
      private String numberVolume;

      @ApiModelProperty("是否可以工作")
    @Alias("是否可以工作")
      private Boolean cabinetIsWork;

      @ApiModelProperty("地址")
    @Alias("地址")
      private String cabinetAddress;

      @ApiModelProperty("维修日期")
    @Alias("维修日期")
      private LocalDate cabinetRepairDate;


}
