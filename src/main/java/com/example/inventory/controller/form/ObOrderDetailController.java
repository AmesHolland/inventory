package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IObOrderDetailService;
import com.example.inventory.entity.form.ObOrderDetail;

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
@RequestMapping("/ob_order_detail")
public class ObOrderDetailController {

    @Resource
    private IObOrderDetailService obOrderDetailService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody ObOrderDetail obOrderDetail) {
        obOrderDetailService.saveOrUpdate(obOrderDetail);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        obOrderDetailService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        obOrderDetailService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(obOrderDetailService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(obOrderDetailService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                           @RequestParam Integer obOrdId) {
        QueryWrapper<ObOrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ob_ord_id",obOrdId );
        return Result.success(obOrderDetailService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

