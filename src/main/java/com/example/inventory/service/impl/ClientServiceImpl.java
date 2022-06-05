package com.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.inventory.entity.info.Client;
import com.example.inventory.entity.info.ClientDTO;
import com.example.inventory.mapper.ClientMapper;
import com.example.inventory.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientDTO> clientList() {
        return clientMapper.clientList();
    }
}
