package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
  @TableName("sale_order")
public class SaleOrder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Timestamp createTime;

    private Integer stId;

    private Float totalAmount;

    private String remark;

    private String submitState;

    private String auditState;

    private Integer clId;

    @TableField(exist = false)
    private String clName;

    private Boolean isDelete;

    private Boolean isOut;

    @TableField(exist = false)
    private String stName;


    private String deId;

}
