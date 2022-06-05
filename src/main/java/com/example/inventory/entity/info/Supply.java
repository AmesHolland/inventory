package com.example.inventory.entity.info;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("supply")
public class Supply {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String brand;
    private String address;
    private String name;
    private String tele;
    private String remark;

}
