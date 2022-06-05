package com.example.inventory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Holland
 * @since 2022-05-30
 */
@Mapper
public interface PurOrderMapper extends BaseMapper<PurOrder> {

    Page<PurOrder> findPage(Page<PurOrder> page, String submitState ,String auditState, String verifyState, String stName);

    PurOrder selectByIdWithMore(@Param("id") Integer id);
}
