package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ib_order")
public class IbOrder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Timestamp createTime;

    private Integer stId;

    @TableField(exist = false)
    private String stName;

    private Integer purOrdId;

    private String submitState;

    private String procState;

    private String remark;

    private Boolean isDelete;

    private String deId;


}
