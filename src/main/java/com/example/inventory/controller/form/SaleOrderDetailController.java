package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrderDetail;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.ISaleOrderDetailService;
import com.example.inventory.entity.form.SaleOrderDetail;

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
@RequestMapping("/sale_order_detail")
public class SaleOrderDetailController {

    @Resource
    private ISaleOrderDetailService saleOrderDetailService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody SaleOrderDetail saleOrderDetail) {

        return Result.success(saleOrderDetailService.saveOrUpdateByCa(saleOrderDetail));
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody SaleOrderDetail saleOrderDetail) {

        return Result.success(saleOrderDetailService.saveOrUpdate(saleOrderDetail));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(saleOrderDetailService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        saleOrderDetailService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(saleOrderDetailService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(saleOrderDetailService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                           @RequestParam Integer sale_ord_id) {

        Page<SaleOrderDetail> page = saleOrderDetailService.findPage(new Page<>(pageNum, pageSize), sale_ord_id);

        return Result.success(page);
    }

}

