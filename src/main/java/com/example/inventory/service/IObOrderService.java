package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.ObOrder;
import com.example.inventory.entity.form.ObOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
public interface IObOrderService extends IService<ObOrder> {

    Page<ObOrder> findPage(Page<ObOrder> page, String submitState, String procState, String stName);

    ObOrder getByIdWithMore(Integer id);
    Boolean outbound(ObOrder obOrder);
}
