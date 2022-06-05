package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrder;
import com.example.inventory.mapper.SaleOrderMapper;
import com.example.inventory.service.ISaleOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@Service
public class SaleOrderServiceImpl extends ServiceImpl<SaleOrderMapper, SaleOrder> implements ISaleOrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Override
    public Page<SaleOrder> findPage(Page<SaleOrder> page, String submitState, String auditState, String clName, String stName) {
        return saleOrderMapper.findPage(page, submitState, auditState,clName, stName);
    }

    @Override
    public SaleOrder getByIdWithMore(Integer id) {
        return saleOrderMapper.getByIdWithMore(id);
    }
}
