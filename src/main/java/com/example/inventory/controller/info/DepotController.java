package com.example.inventory.controller.info;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.entity.info.Depot;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.inventory.common.Result;

import com.example.inventory.service.IDepotService;
import com.example.inventory.entity.info.Depot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Holland
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/depot")
public class DepotController {

    @Resource
    private IDepotService depotService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Depot depot) {
        depotService.saveOrUpdate(depot);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        depotService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        depotService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(depotService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(depotService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String id,
                           @RequestParam String address,
                           @RequestParam String stName
                           ) {

        Page<Depot> page = depotService.findPage(new Page<>(pageNum, pageSize), id, address, stName);
        return Result.success(page);
    }

    @GetMapping("/list")
    public Result getList() {
        return Result.success(depotService.list());
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有数据
        List<Depot> list = depotService.list();
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("仓库信息", "UTF-8");
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
        List<Depot> depots = reader.readAll(Depot.class);
        return Result.success(depotService.saveBatch(depots));
    }

}

