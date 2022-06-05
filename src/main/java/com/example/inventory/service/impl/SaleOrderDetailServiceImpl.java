package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.example.inventory.entity.form.SaleOrderDetail;
import com.example.inventory.mapper.SaleOrderDetailMapper;
import com.example.inventory.mapper.SaleOrderDetailMapper;
import com.example.inventory.service.ISaleOrderDetailService;
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
public class SaleOrderDetailServiceImpl extends ServiceImpl<SaleOrderDetailMapper, SaleOrderDetail> implements ISaleOrderDetailService {

    @Autowired
    private SaleOrderDetailMapper saleOrderDetailMapper;
    
    @Override
    public Page<SaleOrderDetail> findPage(Page<SaleOrderDetail> page, Integer sale_ord_id) {
        return saleOrderDetailMapper.findPage(page ,sale_ord_id);
    }

    @Override
    public Boolean saveOrUpdateByCa(SaleOrderDetail saleOrderDetail) {
        Integer saleOrdId = saleOrderDetail.getSaleOrdId();
        Integer caId = saleOrderDetail.getCaId();

        QueryWrapper<SaleOrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sale_ord_id", saleOrdId);
        queryWrapper.eq("ca_id", caId);

        List<SaleOrderDetail> saleOrderDetails = saleOrderDetailMapper.selectList(queryWrapper);

        if(saleOrderDetails.size() == 0){
            saleOrderDetailMapper.insert(saleOrderDetail);
        }
        else{
            SaleOrderDetail temp = saleOrderDetails.get(0);
            Integer sum = saleOrderDetail.getCaNum() + temp.getCaNum();
            temp.setCaNum(sum);
            saleOrderDetailMapper.updateById(temp);
        }
        return true;
    }
}
