package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrder;
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
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {

    Page<SaleOrder> findPage(Page<SaleOrder> page, String submitState, String auditState, String clName, String stName);

    SaleOrder getByIdWithMore(Integer id);
}
