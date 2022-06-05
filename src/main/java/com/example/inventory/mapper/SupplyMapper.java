package com.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.inventory.entity.info.Supply;
import com.example.inventory.entity.info.SupplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplyMapper extends BaseMapper<Supply> {


    List<SupplyDTO> SupplyBrandList();
}
