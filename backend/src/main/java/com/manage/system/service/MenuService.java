package com.manage.system.service;

import com.manage.common.Constants;
import com.manage.system.bean.Menu;
import com.manage.system.dao.MenuMapper;
import com.manage.system.model.SysMenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * fetch current user's menu
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Menu> getAllMenusWithOutSystem() {

        List<Menu> menus = new ArrayList<>();
        List<SysMenuDto> menuList;
        // 获取用户菜单
        try {
            menuList = menuMapper.selectByParam(null, null, Constants.MENU_TYPE_DEFAULT, null);
        } catch (Exception e) {
            menuList = null;
            e.printStackTrace();
        }
        // 菜单二级设置
        getRootMenus(menus, menuList);

        return menus;
    }

    /**
     * fetch current user's menu
     *
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Menu> getUserMenus(String userId) {

        List<Menu> menus = new ArrayList<>();
        List<SysMenuDto> menuList;
        // 获取用户菜单
        try {
            menuList = menuMapper.selectMenuByUserId(userId);
        } catch (Exception e) {
            menuList = null;
            e.printStackTrace();
        }
        // 菜单二级设置
        getRootMenus(menus, menuList);

        return menus;
    }

    private void getRootMenus(List<Menu> menus, List<SysMenuDto> menuList) {
        // 菜单二级设置
        if (menuList != null && menuList.size() > 0) {
            for (SysMenuDto dto : menuList) {
                if (dto.getParentId() == 0) {
                    Menu menu = new Menu(dto.getId(), dto.getName(), dto.getUrl(), dto.getIcon(), dto.getType());
                    menus.add(menu);
                }
            }
            getSubMenu(menus, menuList);
        }
    }

    /**
     * 获取子菜单
     *
     * @param menus    当前菜单列表
     * @param allMenus 所有菜单
     */
    private void getSubMenu(List<Menu> menus, List<SysMenuDto> allMenus) {
        for (Menu menu : menus) {
            for (SysMenuDto dto : allMenus) {
                if (menu.getMenuId() == dto.getParentId()) {
                    Menu subMenu = new Menu(dto.getId(), dto.getName(), dto.getUrl(), dto.getIcon(), dto.getType());
                    menu.getSubMenus().add(subMenu);
                }
            }
            if (menu.getSubMenus().size() > 0) {
                getSubMenu(menu.getSubMenus(), allMenus);
            }
        }
    }
}
