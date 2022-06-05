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
@TableName("staff")
public class Staff {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String role;
    private String password;
    private String name;
    private String tele;
    private String gender;
    private String address;
    private String avatar;

    public Staff(String role, String password, String name, String tele, String gender, String address,String avatar) {
        this.role = role;
        this.password = password;
        this.name = name;
        this.tele = tele;
        this.gender = gender;
        this.address = address;
        this.avatar = avatar;
    }
}
