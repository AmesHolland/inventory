package com.example.inventory.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {

    private Integer id;
    private String password;
    private String name;
    private String token;
    private String avatar;
    private List<Menu> menus;
    private String role;

    public StaffDTO(Integer id, String password) {
        this.password = password;
        this.id = id;
    }
}