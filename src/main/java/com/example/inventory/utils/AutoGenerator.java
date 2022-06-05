package com.example.inventory.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class AutoGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/imdb?characterEncoding=utf-8&userSSL=false", "root", "root").globalConfig(builder -> {
                    builder.author("summer")
                            // 设置作者
                            //  .enableSwagger()
                            // 开启 swagger 模式
                            .fileOverride()
                            // 覆盖已生成文件
                            .outputDir("D://ProgramFiles//Project Files//Java File//Intellij//Java Web//InventoryExample//Inventory//src//main//java");
                    // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("com.example.inventory")
                            // 设置父包名
                            // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "D://ProgramFiles//Project Files//Java File//Intellij//Java Web//InventoryExample//Inventory//src//main//java//com//example//inventory//mapper"));
                    // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    builder.addInclude("product")
                            // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_");
                    // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine())
                // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
