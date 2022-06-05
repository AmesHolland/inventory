package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
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
  @TableName("ob_order_detail")
public class ObOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer obOrdId;

    private Integer caId;

    private Integer caNum;


}
