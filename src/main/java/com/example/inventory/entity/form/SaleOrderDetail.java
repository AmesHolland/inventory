package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
  @TableName("sale_order_detail")
public class SaleOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer saleOrdId;

    private Integer caId;

    private Integer caNum;

    @TableField(exist = false)
    private float caPrice;

    @TableField(exist = false)
    private String caName;

}
