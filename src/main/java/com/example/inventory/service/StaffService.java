package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.inventory.common.Constants;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.sys.StaffDTO;
import com.example.inventory.entity.utils.StaffPasswordDTO;
import com.example.inventory.exception.ServiceException;

public interface StaffService extends IService<Staff> {

    public Result login(StaffDTO staffDTO);

    public void updatePassword(StaffPasswordDTO staffPasswordDTO);
}
