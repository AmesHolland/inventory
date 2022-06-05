package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@Getter
@Setter
  @TableName("ob_order")
public class ObOrder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Timestamp createTime;

    private Integer stId;

    private String submitState;

    private String procState;

    private String remark;

    private Boolean isDelete;

    @TableField(exist = false)
    private String stName;

    private Integer SaleOrdId;

    private String deId;


}
