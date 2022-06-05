package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrderDetail;
import com.example.inventory.mapper.PurOrderDetailMapper;
import com.example.inventory.service.IPurOrderDetailService;
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
 * @since 2022-05-31
 */
@Service
public class PurOrderDetailServiceImpl extends ServiceImpl<PurOrderDetailMapper, PurOrderDetail> implements IPurOrderDetailService {

    @Autowired
     private PurOrderDetailMapper purOrderDetailMapper;

    @Override
    public Page<PurOrderDetail>  findPage(Page<PurOrderDetail> page , Integer pur_ord_id){

        return purOrderDetailMapper.findPage(page ,pur_ord_id);

    }

    @Override
    public Boolean saveOrUpdateByPro(PurOrderDetail purOrderDetail) {

        Integer purOrdId = purOrderDetail.getPurOrdId();
        Integer prId = purOrderDetail.getPrId();

        QueryWrapper<PurOrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pur_ord_id", purOrdId);
        queryWrapper.eq("pr_id", prId);

        List<PurOrderDetail> purOrderDetails = purOrderDetailMapper.selectList(queryWrapper);

        if(purOrderDetails.size() == 0){
            purOrderDetailMapper.insert(purOrderDetail);
        }
        else{
            PurOrderDetail temp = purOrderDetails.get(0);
            Integer sum = purOrderDetail.getPrNum() + temp.getPrNum();
            temp.setPrNum(sum);
            purOrderDetailMapper.updateById(temp);
        }

        return true;
    }

}
