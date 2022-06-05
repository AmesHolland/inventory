package com.example.inventory.controller.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.inventory.common.Result;
import com.example.inventory.entity.utils.Files;
import com.example.inventory.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper filesMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 添加一个文件标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        //判断配置的文件目录是否存在，若不存在泽创建一个新的文件目录
        if(!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdirs();
        }

        String md5;
        String url;

        file.transferTo(uploadFile);
        md5 = SecureUtil.md5(uploadFile);
        Files dbFiles = getFileByMd5(md5);

        if(dbFiles != null){
            url = dbFiles.getUrl();
            uploadFile.delete();
        }else{
            url = "http://localhost:9090/file/" + fileUuid;
        }

        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize( size / 1024);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        filesMapper.insert(saveFiles);
        return url;

    }

    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUuid);

        ServletOutputStream os = response.getOutputStream();

        response.addHeader("Content-Disposition","attachment;filename" + URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");

        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

    }


    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = filesMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }


    //分页查询 Mybatis
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        IPage<Files> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",false);
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }
        return Result.success(filesMapper.selectPage(page,queryWrapper));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files){
        return Result.success(filesMapper.updateById(files));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){

        Files files = filesMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(filesMapper.updateById(files));
    }


    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = filesMapper.selectList(queryWrapper);

        for(Files file : files){
            file.setIsDelete(true);
            filesMapper.updateById(file);
        }
        return Result.success();
    }

}

