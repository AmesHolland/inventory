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
 * @since 2022-06-02
 */
@Getter
@Setter
  @TableName("ib_order_detail")
public class IbOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer ibOrdId;

    private Integer prId;

    private Integer prNum;


}
