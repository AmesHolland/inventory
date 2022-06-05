package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Depot;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
public interface IDepotService extends IService<Depot> {

    public Page<Depot> findPage(Page<Depot> page, String id, String address, String stName);
}
