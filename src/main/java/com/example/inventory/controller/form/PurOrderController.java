package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.utils.TokenUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.inventory.service.IPurOrderService;
import com.example.inventory.entity.form.PurOrder;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/pur_order")
public class PurOrderController {

    @Resource
    private IPurOrderService purOrderService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody PurOrder purOrder) {

        if(purOrderService.saveOrUpdate(purOrder)){
            return Result.success();
        }
        else{
            return Result.error();
        }

    }

    @PostMapping("/sub")
    public Result sub(@RequestBody PurOrder purOrder) {

        purOrder.setSubmitState("已提交");

        if(purOrderService.saveOrUpdate(purOrder)){
            return Result.success();
        }
        else{
            return Result.error();
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        PurOrder purOrder = purOrderService.getById(id);

        purOrder.setIsDelete(true);

        return Result.success(purOrderService.updateById(purOrder));

    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {


        QueryWrapper<PurOrder> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("id",ids);
        List<PurOrder> list = purOrderService.list(queryWrapper);
        for(PurOrder p : list){
            p.setIsDelete(true);
            purOrderService.updateById(p);
        }

        return Result.success();

    }

    @GetMapping
    public Result findAll() {
        return Result.success(purOrderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(purOrderService.getByIdWithMore(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String submitState,
                           @RequestParam String auditState,
                           @RequestParam String verifyState,
                           @RequestParam String stName
    ) {

        Staff currentUser = TokenUtils.getCurrentUser();

        Page<PurOrder> page = null;

        if(currentUser.getRole() != null && Objects.equals(currentUser.getRole(), "管理员")){
        }
        else{
            stName = currentUser.getName();
        }

        page = purOrderService.findPage(new Page<>(pageNum, pageSize),submitState, auditState, verifyState, stName);



        return Result.success(page);
    }

}

