package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IIbOrderDetailService;
import com.example.inventory.entity.form.IbOrderDetail;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/ib_order_detail")
public class IbOrderDetailController {

    @Resource
    private IIbOrderDetailService ibOrderDetailService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody IbOrderDetail ibOrderDetail) {
        ibOrderDetailService.saveOrUpdate(ibOrderDetail);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ibOrderDetailService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        ibOrderDetailService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(ibOrderDetailService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(ibOrderDetailService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                           @RequestParam Integer ibOrdId) {
        QueryWrapper<IbOrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ib_ord_id", ibOrdId);
        return Result.success(ibOrderDetailService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

