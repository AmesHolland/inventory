package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Depot;
import com.example.inventory.mapper.DepotMapper;
import com.example.inventory.service.IDepotService;
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
public class DepotServiceImpl extends ServiceImpl<DepotMapper, Depot> implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;

    @Override
    public Page<Depot> findPage(Page<Depot> page, String id, String address, String stName) {
        return depotMapper.findPage(page, id, address, stName);
    }
}
