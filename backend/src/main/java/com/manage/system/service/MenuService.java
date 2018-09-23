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

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * @param menuName 菜单名
     * @param parentId 父级菜单id
     * @param type     菜单类型，1：系统，2：文档
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Menu> getAllMenusWithOutSystem(String menuName, Integer parentId, Integer type) {

        List<Menu> menus = new ArrayList<>();
        List<SysMenuDto> menuList;
        // 获取用户菜单
        try {
            menuList = menuMapper.selectByParam(menuName, parentId, type);
        } catch (Exception e) {
            menuList = null;
            e.printStackTrace();
        }
        // 菜单二级设置
        getRootMenus(menus, menuList);

        return menus;
    }

    /**
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

    /**
     * @param roleId
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Menu> getMenusByRole(Integer roleId, Integer type) {

        List<Menu> menus = new ArrayList<>();
        List<SysMenuDto> menuList;
        // 获取用户菜单
        try {
            menuList = menuMapper.selectMenuByRoleId(roleId, type);
        } catch (Exception e) {
            menuList = null;
            e.printStackTrace();
        }
        // 菜单二级设置
        getRootMenus(menus, menuList);

        return menus;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    /*插入角色菜单表数据*/
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertRoleMenu(Integer roleId, String[] menuList) throws Exception {
        // 删除菜单关系
        roleMenuService.deleteByPrimaryKey(null,roleId,Constants.MENU_TYPE_DEFAULT);
        for (String id : menuList) {
            roleMenuService.insertRoleMenu(Integer.parseInt(id), roleId);
        }
    }
}
