package com.example.inventory.controller.info;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Client;
import com.example.inventory.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {


    @Autowired
    private ClientService clientService;

    // 增加修改
    @PostMapping
    public Result save(@RequestBody Client client){
        return Result.success(clientService.saveOrUpdate(client));
    }

    //查询
    @GetMapping
    public Result findAll(){
        return Result.success(clientService.list());
    }

    @GetMapping("/clientList")
    public Result selectClientList(){
        return Result.success(clientService.clientList());
    }


    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(clientService.removeById(id));
    }

    @GetMapping("/id/{id}")
    public Result findOne(@PathVariable Integer id){
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Result.success(clientService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(clientService.removeBatchByIds(ids));
    }

    //分页查询 Mybatis
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String tele,
                           @RequestParam(defaultValue = "") String address){
        IPage<Client> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }
        if(!"".equals(tele)){
            queryWrapper.like("tele", tele);
        }
        if(!"".equals(address)){
            queryWrapper.like("address", address);
        }

        return Result.success(clientService.page(page,queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有数据
        List<Client> list = clientService.list();
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("客户信息", "UTF-8");
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
        List<Client> clients = reader.readAll(Client.class);
        return Result.success(clientService.saveBatch(clients));
    }


}

