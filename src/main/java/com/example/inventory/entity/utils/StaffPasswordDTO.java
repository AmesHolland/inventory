package com.example.inventory.entity.utils;

import lombok.Data;

/**
 * @param id
 * @author 11541
 * @date 20:28 2022/6/4
 * @return
 */
@Data
public class StaffPasswordDTO {

    private int id;
    private String password;
    private String newPassword;
    private String confirmPassword;

}
