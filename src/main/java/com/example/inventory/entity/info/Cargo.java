package com.example.inventory.entity.info;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

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
  public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String type;

    private Float price;

    private String image;

    private String description;

    @TableField(exist = false)
    private int stock;

    @TableField(exist = false)
    private Integer num = 1;



}
