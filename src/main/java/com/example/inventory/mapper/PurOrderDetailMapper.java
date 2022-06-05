package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Holland
 * @since 2022-05-31
 */
@Mapper
public interface PurOrderDetailMapper extends BaseMapper<PurOrderDetail> {

    Page<PurOrderDetail> findPage(Page<PurOrderDetail> page, Integer pur_ord_id);
}
