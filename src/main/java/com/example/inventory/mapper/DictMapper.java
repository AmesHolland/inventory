package com.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.inventory.entity.sys.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
