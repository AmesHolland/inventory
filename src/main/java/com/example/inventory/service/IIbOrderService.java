package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.IbOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.inventory.entity.form.PurOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
public interface IIbOrderService extends IService<IbOrder> {


    Page<IbOrder> findPage(Page<IbOrder> page, String submitState, String procState, String stName);

    IbOrder getByIdWithMore(Integer id);
}
