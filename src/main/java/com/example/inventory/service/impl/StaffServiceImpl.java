package com.example.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.inventory.common.Constants;
import com.example.inventory.common.Result;
import com.example.inventory.entity.info.Staff;
import com.example.inventory.entity.sys.Menu;
import com.example.inventory.entity.sys.StaffDTO;
import com.example.inventory.entity.utils.StaffPasswordDTO;
import com.example.inventory.exception.ServiceException;
import com.example.inventory.mapper.RoleMapper;
import com.example.inventory.mapper.RoleMenuMapper;
import com.example.inventory.mapper.StaffMapper;
import com.example.inventory.service.IMenuService;
import com.example.inventory.service.StaffService;
import com.example.inventory.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {


    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    public Result login(StaffDTO staffDTO) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", staffDTO.getId());
        queryWrapper.eq("password", staffDTO.getPassword());
        Staff one = getOne(queryWrapper);
        if(one != null){
            BeanUtil.copyProperties(one,staffDTO,true);
            //设置 token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            staffDTO.setToken(token);

            String role = one.getRole();

            // 设置用户菜单列表
            List<Menu> roleMenus = this.getRoleMenus(role);

            staffDTO.setMenus(roleMenus);

            return Result.success(staffDTO);
        }
        else{
            return Result.error(Constants.CODE_600,"用户名或密码错误");
        }

    }

    @Override
    public void updatePassword(StaffPasswordDTO staffPasswordDTO) {
        int update = staffMapper.updatePassword(staffPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }
    private List<Menu> getRoleMenus(String name){

        Integer roleId = roleMapper.selectByFlag(name);

        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出所有菜单
        List<Menu> menus = menuService.findMenus("");

        //筛选完成之后的结果
        List<Menu> roleMenus = new ArrayList<Menu>();

        // 筛选当前用户角色的菜单
        for(Menu menu : menus){
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }

        return roleMenus;
    }

}
