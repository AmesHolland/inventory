package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.IbOrderDetail;
import com.example.inventory.entity.form.ObOrder;
import com.example.inventory.entity.form.ObOrderDetail;
import com.example.inventory.entity.info.Depot;
import com.example.inventory.entity.info.DepotDetail;
import com.example.inventory.mapper.ObOrderMapper;
import com.example.inventory.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@Service
public class ObOrderServiceImpl extends ServiceImpl<ObOrderMapper, ObOrder> implements IObOrderService {

    @Autowired
    private ObOrderMapper obOrderMapper;

    @Autowired
    private IDepotDetailService depotDetailService;

    @Autowired
    private IObOrderDetailService obOrderDetailService;


    @Override
    public Page<ObOrder> findPage(Page<ObOrder> page, String submitState, String procState, String stName) {
        return obOrderMapper.findPage(page, submitState,procState,stName);
    }

    @Override
    public ObOrder getByIdWithMore(Integer id) {
        return obOrderMapper.getByIdWithMore(id);
    }

    @Override
    public Boolean outbound(ObOrder obOrder) {

        String deId = obOrder.getDeId();
        Integer id = obOrder.getId();

        QueryWrapper<ObOrderDetail> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("ob_ord_id",id);
        List<ObOrderDetail> list = obOrderDetailService.list(queryWrapper);

        QueryWrapper<DepotDetail> queryWrapper2 = new QueryWrapper<>();

        for(ObOrderDetail o : list){
            Integer caId = o.getCaId();
            Integer caNum = o.getCaNum();

            queryWrapper2.eq("de_id", deId);
            queryWrapper2.eq("ca_id",caId);
            List<DepotDetail> list1 = depotDetailService.list(queryWrapper2);
            DepotDetail depotDetail = list1.get(0);
            if(depotDetail.getCaNum() < caNum){
                return false;
            }

        }
        return true;

    }
}
