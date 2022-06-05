package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.SaleOrder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.inventory.common.Result;

import com.example.inventory.service.ISaleOrderService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/sale_order")
public class SaleOrderController {

    @Resource
    private ISaleOrderService saleOrderService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody SaleOrder saleOrder) {
        saleOrderService.saveOrUpdate(saleOrder);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        saleOrderService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        saleOrderService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(saleOrderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(saleOrderService.getByIdWithMore(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String submitState,
                           @RequestParam String auditState,
                           @RequestParam String clName,
                           @RequestParam String stName
    ) {

        Page<SaleOrder> page = saleOrderService.findPage(new Page<>(pageNum, pageSize),submitState, auditState, clName, stName);
        return Result.success(page);
    }

}

