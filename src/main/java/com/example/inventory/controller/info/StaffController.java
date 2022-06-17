package com.example.inventory.controller.info;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Constants;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.info.Supply;
import com.example.inventory.entity.sys.StaffDTO;
import com.example.inventory.entity.utils.StaffPasswordDTO;
import com.example.inventory.service.StaffService;
import com.example.inventory.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/staff")
public class StaffController {


    @Autowired
    private StaffService staffService;

    // 增加修改
    @PostMapping
    public Result save(@RequestBody Staff staff){
        return Result.success(staffService.saveOrUpdate(staff));
    }


    //查询
    @GetMapping
    public Result findAll(){
        return Result.success(staffService.list());
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(staffService.removeById(id));
    }

    @GetMapping("/id/{id}")
    public Result findOne(@PathVariable Integer id){
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Result.success(staffService.getOne(queryWrapper));
    }

    @GetMapping("/{id}/{phone}")
    public Result findOne(@PathVariable Integer id,@PathVariable String phone){
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("phone",phone);
        return Result.success(staffService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(staffService.removeBatchByIds(ids));
    }

    //分页查询 Mybatis
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String role,
                           @RequestParam(defaultValue = "") String address){
        IPage<Staff> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }
        if(!"".equals(role)){
            queryWrapper.like("role", role);
        }
        if(!"".equals(address)){
            queryWrapper.like("address", address);
        }

        return Result.success(staffService.page(page,queryWrapper));
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody StaffDTO staffDTO){

        Integer id = staffDTO.getId();
        String password = staffDTO.getPassword();
        if(id == null || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return staffService.login(staffDTO);
    }

    @PostMapping("/get_password")
    public Result getPassword(@RequestBody Staff staff){

        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",staff.getId());
        queryWrapper.eq("tele",staff.getTele());

        Staff s = staffService.getOne(queryWrapper);

        if(s != null){
            return Result.success(s);
        }else{
            return Result.error("","未查找到正确信息，请重新确认输入的信息");
        }

    }

    @PostMapping("/password")
    public Result password(@RequestBody StaffPasswordDTO staffPasswordDTO) {

        staffService.updatePassword(staffPasswordDTO);
        return Result.success();
    }


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有数据
        List<Staff> list = staffService.list();
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
//        writer.addHeaderAlias("staffname","用户名");
//        writer.addHeaderAlias("password","密码");
//        writer.addHeaderAlias("nickname","昵称");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","电话");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("createTime","创建时间");
//        writer.addHeaderAlias("avatarUrl","头像");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("员工信息", "UTF-8");
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
        List<Staff> staffs = reader.readAll(Staff.class);
        return Result.success(staffService.saveBatch(staffs));
    }


}
