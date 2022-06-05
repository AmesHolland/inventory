package com.example.inventory.service.impl;

import com.example.inventory.entity.info.Product;
import com.example.inventory.mapper.ProductMapper;
import com.example.inventory.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author summer
 * @since 2022-05-28
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    public List<Product> selectAllWithSupply(){
        return productMapper.selectAllWithSupply();
    }

}
