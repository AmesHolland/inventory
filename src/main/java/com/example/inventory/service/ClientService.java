package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.inventory.entity.info.Client;
import com.example.inventory.entity.info.ClientDTO;
import java.util.List;

public interface ClientService extends IService<Client> {
    List<ClientDTO> clientList();
}
