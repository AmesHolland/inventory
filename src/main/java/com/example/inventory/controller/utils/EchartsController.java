package com.example.inventory.controller.utils;

import cn.hutool.core.collection.CollUtil;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Depot;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.utils.Balance;
import com.example.inventory.entity.utils.Pie;
import com.example.inventory.mapper.EchartsMapper;
import com.example.inventory.mapper.FilesMapper;
import com.example.inventory.service.IDepotService;
import com.example.inventory.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsMapper echartsMapper;

    @Autowired
    private IDepotService depotService;

    @GetMapping("/balance")
    public Result getBalance() {

        List<Balance> balance = echartsMapper.getBalance();
        List<Depot> list = depotService.list();

        List<String> date = new ArrayList<>();
        List<Float> pur = new ArrayList<>();
        List<Float> sale = new ArrayList<>();

        Float purTotal = Float.valueOf(0);
        Float saleTotal = Float.valueOf(0);

        for(Balance b : balance){
            date.add(b.getDate().toString());
            pur.add(b.getPur());
            purTotal += b.getPur();
            sale.add(b.getSale());
            saleTotal += b.getSale();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("x", date);
        map.put("pur", pur);
        map.put("sale",sale);
        map.put("purTotal", purTotal);
        map.put("saleTotal", saleTotal);
        map.put("depotNum", list.size());

        return Result.success(map);
    }


    @GetMapping("/members")
    public Result members() {

        List<Pie> pie = echartsMapper.getPie();

        return Result.success(pie);
    }


}
