package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IPurOrderDetailService;
import com.example.inventory.entity.form.PurOrderDetail;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/pur_order_detail")
public class PurOrderDetailController {

    @Resource
    private IPurOrderDetailService purOrderDetailService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody PurOrderDetail purOrderDetail) {

        return Result.success(purOrderDetailService.saveOrUpdateByPro(purOrderDetail));
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody PurOrderDetail purOrderDetail) {

        return Result.success(purOrderDetailService.saveOrUpdate(purOrderDetail));
    }




    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        return Result.success(purOrderDetailService.removeById(id));

    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {


        return Result.success(purOrderDetailService.removeBatchByIds(ids));

    }

    @GetMapping
    public Result findAll() {
        return Result.success(purOrderDetailService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(purOrderDetailService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam Integer pur_ord_id) {

        Page<PurOrderDetail> page = purOrderDetailService.findPage(new Page<>(pageNum, pageSize),pur_ord_id);

        return Result.success(page);
    }

}

