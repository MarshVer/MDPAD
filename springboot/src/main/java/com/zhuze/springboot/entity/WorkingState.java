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
 * @since 2023-05-08
 */
@Getter
@Setter
  @TableName("working_state")
@ApiModel(value = "WorkingState对象", description = "")
public class WorkingState implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "working_state_id", type = IdType.AUTO)
      private Integer workingStateId;

    private String workingState;

    private Integer workingFlag;


}
