package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
public interface ISaleOrderDetailService extends IService<SaleOrderDetail> {

    public Page<SaleOrderDetail> findPage(Page<SaleOrderDetail> page , Integer sale_ord_id);

    Boolean saveOrUpdateByCa(SaleOrderDetail saleOrderDetail);


}
