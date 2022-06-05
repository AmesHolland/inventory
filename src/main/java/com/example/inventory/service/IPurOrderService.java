package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-05-30
 */
public interface IPurOrderService extends IService<PurOrder> {

    Page<PurOrder> findPage(Page<PurOrder> objectPage,String submitState ,  String auditState, String verifyState, String stName);

    PurOrder getByIdWithMore(Integer id);
}
