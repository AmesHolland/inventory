package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-05-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
  @TableName("pur_order_detail")
public class PurOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer purOrdId;

    private Integer prId;

    private Integer prNum;

    @TableField(exist = false)
    private float prPrice;

    @TableField(exist = false)
    private String prName;


}
