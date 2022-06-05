package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Cargo;
import com.example.inventory.mapper.CargoMapper;
import com.example.inventory.service.ICargoService;
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
public class CargoServiceImpl extends ServiceImpl<CargoMapper, Cargo> implements ICargoService {

    @Autowired
    private CargoMapper cargoMapper;

    @Override
    public Page<Cargo> findPage(Page<Cargo> page, String name, String type, String deId) {
        return cargoMapper.findPage(page, name, type, deId);
    }
}
