package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import com.example.inventory.mapper.PurOrderMapper;
import com.example.inventory.service.IPurOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Holland
 * @since 2022-05-30
 */
@Service
public class PurOrderServiceImpl extends ServiceImpl<PurOrderMapper, PurOrder> implements IPurOrderService {


    @Autowired
    private PurOrderMapper purOrderMapper;

    @Override
    public Page<PurOrder> findPage(Page<PurOrder> page,String submitState, String auditState, String verifyState, String stName) {
        return purOrderMapper.findPage(page, submitState,auditState, verifyState,stName);
    }

    @Override
    public PurOrder getByIdWithMore(Integer id) {
        return purOrderMapper.selectByIdWithMore(id);
    }
}
