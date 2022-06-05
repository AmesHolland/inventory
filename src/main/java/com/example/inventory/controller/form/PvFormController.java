package com.example.inventory.controller.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.form.PurOrder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IPvFormService;
import com.example.inventory.entity.form.PvForm;

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
@RequestMapping("/pv_form")
public class PvFormController {

    @Resource
    private IPvFormService pvFormService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody PvForm pvForm) {
        pvFormService.saveOrUpdate(pvForm);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        PvForm pvForm = pvFormService.getById(id);
       pvForm.setIsDelete(true);
       return Result.success(pvFormService.updateById(pvForm));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {

        QueryWrapper<PvForm> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("id",ids);
        List<PvForm> list = pvFormService.list(queryWrapper);
        for(PvForm p : list){
            p.setIsDelete(true);
            pvFormService.updateById(p);
        }

        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(pvFormService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(pvFormService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<PvForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("is_delete",false);
        return Result.success(pvFormService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

