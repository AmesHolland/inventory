package com.example.inventory.entity.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @param id
 * @author 11541
 * @date 20:51 2022/6/4
 * @return
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    private Date date;
    private Float pur;
    private Float sale;

}
