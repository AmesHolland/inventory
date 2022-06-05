package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Depot;
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
public interface DepotMapper extends BaseMapper<Depot> {

    Page<Depot> findPage(Page<Depot> page, String id, String address, String stName);
}
