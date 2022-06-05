package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Cargo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
public interface ICargoService extends IService<Cargo> {

    public Page<Cargo> findPage(Page<Cargo> page, String name, String type,String deId);
}
