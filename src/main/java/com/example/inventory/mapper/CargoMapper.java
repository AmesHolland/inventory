package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Cargo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@Mapper
public interface CargoMapper extends BaseMapper<Cargo> {

    Page<Cargo> findPage(Page<Cargo> page, String name, String type, String deId);
}
