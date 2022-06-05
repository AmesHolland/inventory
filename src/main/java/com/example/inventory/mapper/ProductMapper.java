package com.example.inventory.mapper;

import com.example.inventory.entity.info.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author summer
 * @since 2022-05-28
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> selectAllWithSupply();



}
