package com.example.inventory.mapper;

import com.example.inventory.entity.utils.Balance;
import com.example.inventory.entity.utils.Pie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @param id
 * @author 11541
 * @date 20:54 2022/6/4
 * @return
 */
@Mapper
public interface EchartsMapper {

    List<Balance> getPurchase();
    List<Balance> getSale();
    List<Balance> getBalance();

    @Select("select role name, count(role) value from staff group by role")
    List<Pie> getPie();

    @Select("select count(*) from staff")
    Integer getNum();

}
