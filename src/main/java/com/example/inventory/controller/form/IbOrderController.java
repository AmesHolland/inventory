package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import com.example.inventory.entity.form.PurOrderDetail;
import com.example.inventory.service.IPurOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IIbOrderService;
import com.example.inventory.entity.form.IbOrder;

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
@RequestMapping("/ib_order")
public class IbOrderController {

    @Resource
    private IIbOrderService ibOrderService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody IbOrder ibOrder) {
        ibOrderService.saveOrUpdate(ibOrder);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        IbOrder ibOrder = ibOrderService.getById(id);

        ibOrder.setIsDelete(true);
        ibOrderService.updateById(ibOrder);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        List<IbOrder> list = ibOrderService.list();
        for(IbOrder ibOrder : list){
            ibOrder.setIsDelete(true);
            ibOrderService.updateById(ibOrder);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(ibOrderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(ibOrderService.getByIdWithMore(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String submitState,
                           @RequestParam String procState,
                           @RequestParam String stName) {
        Page<IbOrder> page = ibOrderService.findPage(new Page<>(pageNum, pageSize), submitState, procState, stName);
        return Result.success(page);
    }

}

