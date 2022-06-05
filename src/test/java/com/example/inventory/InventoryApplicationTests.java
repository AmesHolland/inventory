package com.example.inventory;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.form.PurOrder;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.service.IPurOrderService;
import com.example.inventory.service.ProductService;
import com.example.inventory.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class InventoryApplicationTests {

    @Autowired
    private StaffService staffService;

    @Autowired
    private ProductService productService;

    @Autowired
    private IPurOrderService purOrderService;

    @Test
    void contextLoads() {
    }

}
