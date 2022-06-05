package com.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.utils.StaffPasswordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

    @Update("update staff set password = #{newPassword} where id = #{id} and password = #{password}")
    int updatePassword(StaffPasswordDTO staffPasswordDTO);
}
