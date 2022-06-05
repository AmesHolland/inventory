package com.example.inventory.entity.info;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@Getter
@Setter
  public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;

      private String id;

    private String address;

    private Integer stId;

    private Integer capacity;

    private Integer balance;

    private String remark;

    @TableField(exist = false)
    private String stName;


}
