package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-05-31
 */
public interface IPurOrderDetailService extends IService<PurOrderDetail> {

    public Page<PurOrderDetail> findPage(Page<PurOrderDetail> page , Integer pur_ord_id);

    Boolean saveOrUpdateByPro(PurOrderDetail purOrderDetail);
}
