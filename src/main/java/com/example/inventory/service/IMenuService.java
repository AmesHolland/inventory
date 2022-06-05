package com.example.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.inventory.entity.sys.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-02-10
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
