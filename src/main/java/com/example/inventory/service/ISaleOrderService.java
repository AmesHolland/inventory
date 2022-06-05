package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
public interface ISaleOrderService extends IService<SaleOrder> {

    Page<SaleOrder> findPage(Page<SaleOrder> page, String submitState, String auditState, String clName, String stName);

    SaleOrder getByIdWithMore(Integer id);
}
