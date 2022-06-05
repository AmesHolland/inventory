package com.example.inventory.controller.info;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Product;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author summer
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 增加修改
    @PostMapping
    public Result save(@RequestBody Product product){
        return Result.success(productService.saveOrUpdate(product));
    }

    //查询
    @GetMapping
    public Result findAll(){
        return Result.success(productService.list());
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(productService.removeById(id));
    }

    @GetMapping("/id/{id}")
    public Result findOne(@PathVariable Integer id){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Result.success(productService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(productService.removeBatchByIds(ids));
    }

    //分页查询 Mybatis
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String type,
                           @RequestParam(defaultValue = "") String suName){
        IPage<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }
        if(!"".equals(type)){
            queryWrapper.like("type", type);
        }
        if(!"".equals(suName)){
            queryWrapper.like("su_name", suName);
        }

        return Result.success(productService.page(page,queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有数据
        List<Product> list = productService.list();
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("商品信息", "UTF-8");
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
        List<Product> products = reader.readAll(Product.class);
        return Result.success(productService.saveBatch(products));
    }
}
