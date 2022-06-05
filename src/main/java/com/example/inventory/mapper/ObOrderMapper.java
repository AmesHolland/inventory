package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.IbOrder;
import com.example.inventory.entity.form.ObOrder;
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
public interface ObOrderMapper extends BaseMapper<ObOrder> {


    Page<ObOrder> findPage(Page<ObOrder> page, String submitState, String procState, String stName);

    ObOrder getByIdWithMore(Integer id);

}
