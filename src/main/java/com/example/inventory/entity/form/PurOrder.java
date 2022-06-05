package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pur_order")
public class PurOrder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Timestamp createTime;

    private Integer stId;

    private String submitState;

    private String auditState;

    private String verifyState;

    private Float totalAmount;

    private Integer totalNum;

    private String remark;

    private Boolean isDelete;

    private Boolean isIn;

    @TableField(exist = false)
    private String stName;

}
