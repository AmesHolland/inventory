package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.inventory.entity.info.Supply;
import com.example.inventory.entity.info.SupplyDTO;
import com.example.inventory.mapper.SupplyMapper;
import com.example.inventory.service.SupplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {

    @Resource
    private SupplyMapper supplyMapper;

    @Override
    public List<SupplyDTO> SupplyBrandList() {
        return supplyMapper.SupplyBrandList();
    }

}
