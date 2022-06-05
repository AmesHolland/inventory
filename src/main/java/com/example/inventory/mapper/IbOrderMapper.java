package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.IbOrder;
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
public interface IbOrderMapper extends BaseMapper<IbOrder> {

    Page<IbOrder> findPage(Page<IbOrder> page, String submitState, String procState, String stName);

    IbOrder getByIdWithMore(Integer id);
}
