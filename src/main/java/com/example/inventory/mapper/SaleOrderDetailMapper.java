package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@Mapper
public interface SaleOrderDetailMapper extends BaseMapper<SaleOrderDetail> {

    Page<SaleOrderDetail> findPage(Page<SaleOrderDetail> page, Integer sale_ord_id);

}
