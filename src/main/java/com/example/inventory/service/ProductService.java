package com.example.inventory.service;

import com.example.inventory.entity.info.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author summer
 * @since 2022-05-28
 */
public interface ProductService extends IService<Product> {

    List<Product> selectAllWithSupply();



}
