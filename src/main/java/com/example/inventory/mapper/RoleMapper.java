package com.example.inventory.mapper;

import com.example.inventory.entity.sys.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where name = #{name}")
    Integer selectByFlag(@Param("name") String name);
}
