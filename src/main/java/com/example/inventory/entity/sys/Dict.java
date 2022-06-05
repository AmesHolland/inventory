package com.example.inventory.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_dict")
@Data
public class Dict {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String value;
    private String type;

}
