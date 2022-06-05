package com.example.inventory.controller.info;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.info.Supply;
import com.example.inventory.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {


    @Autowired
    private SupplyService supplyService;

    // 增加修改
    @PostMapping
    public Result save(@RequestBody Supply supply){
        return Result.success(supplyService.saveOrUpdate(supply));
    }

    //查询
    @GetMapping
    public Result findAll(){
        return Result.success(supplyService.list());
    }

    @GetMapping("/brandList")
    public Result selectBrandList(){
        return Result.success(supplyService.SupplyBrandList());
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(supplyService.removeById(id));
    }

    @GetMapping("/id/{id}")
    public Result findOne(@PathVariable Integer id){
        QueryWrapper<Supply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Result.success(supplyService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(supplyService.removeBatchByIds(ids));
    }

    //分页查询 Mybatis

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                @RequestParam Integer pageSize,
                @RequestParam(defaultValue = "") String name,
                @RequestParam(defaultValue = "") String brand,
                @RequestParam(defaultValue = "") String address){
            IPage<Supply> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Supply> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("id");
            if(!"".equals(name)){
                queryWrapper.like("name", name);
            }
            if(!"".equals(brand)){
                queryWrapper.like("brand", brand);
            }
            if(!"".equals(address)){
                queryWrapper.like("address", address);
            }
        return Result.success(supplyService.page(page,queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有数据
        List<Supply> list = supplyService.list();
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("供应商信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Supply> supplys = reader.readAll(Supply.class);
        return Result.success(supplyService.saveBatch(supplys));
    }


}

