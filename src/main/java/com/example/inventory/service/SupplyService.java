package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.inventory.entity.info.Supply;
import com.example.inventory.entity.info.SupplyDTO;

import java.util.List;


public interface SupplyService extends IService<Supply> {

    List<SupplyDTO> SupplyBrandList();

}
