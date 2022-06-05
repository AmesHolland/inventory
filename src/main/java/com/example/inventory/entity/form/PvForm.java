package com.example.inventory.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-05-31
 */
@Getter
@Setter
  @TableName("pv_form")
public class PvForm implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Timestamp createTime;

    private Integer purOrdId;

    private String verifyState;

    private Integer stId;

    private Boolean isDelete;


}
