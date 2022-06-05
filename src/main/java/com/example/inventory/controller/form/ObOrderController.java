package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.form.ObOrder;
import com.example.inventory.service.IObOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/ob_order")
public class ObOrderController {

    @Resource
    private IObOrderService obOrderService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody ObOrder obOrder) {

        if(obOrderService.outbound(obOrder)){
            obOrderService.saveOrUpdate(obOrder);
            return Result.success();
        }
        else{
            return Result.error();
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ObOrder obOrder = obOrderService.getById(id);

        obOrder.setIsDelete(true);
        obOrderService.updateById(obOrder);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        List<ObOrder> list = obOrderService.list();
        for(ObOrder obOrder : list){
            obOrder.setIsDelete(true);
            obOrderService.updateById(obOrder);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(obOrderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(obOrderService.getByIdWithMore(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String submitState,
                           @RequestParam String procState,
                           @RequestParam String stName) {
        Page<ObOrder> page = obOrderService.findPage(new Page<>(pageNum, pageSize), submitState, procState, stName);
        return Result.success(page); }

}

