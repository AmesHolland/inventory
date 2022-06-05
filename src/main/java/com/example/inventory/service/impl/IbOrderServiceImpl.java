package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.IbOrder;
import com.example.inventory.mapper.IbOrderMapper;
import com.example.inventory.service.IIbOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@Service
public class IbOrderServiceImpl extends ServiceImpl<IbOrderMapper, IbOrder> implements IIbOrderService {

    @Autowired
    private IbOrderMapper ibOrderMapper;

    @Override
    public Page<IbOrder> findPage(Page<IbOrder> page, String submitState, String procState, String stName) {
        return ibOrderMapper.findPage(page, submitState,procState,stName);
    }

    @Override
    public IbOrder getByIdWithMore(Integer id) {
        return ibOrderMapper.getByIdWithMore(id);
    }
}
