package com.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.inventory.entity.info.Client;
import com.example.inventory.entity.info.ClientDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    List<ClientDTO> clientList();
}
