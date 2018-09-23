package com.manage.system.controller;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.util.StringUtil;
import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.UrlConstants;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.system.bean.Menu;
import com.manage.system.service.MenuService;
import com.manage.system.service.RoleMenuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UrlConstants.URL_MENU_MODEL)
public class MenuController extends BaseController {

    Log logger = LogFactory.getLog(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 获取用户拥有的菜单列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse getLoginUserMenus() {
        String useId = super.getAccount();
        List<Menu> menus = menuService.getUserMenus(useId);
        return response(menus);
    }

    /**
     * 系统管理以外的所有菜单
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Menu> getAllMenus(@RequestParam(value = "menuName", required = false) String menuName,
                                  @RequestParam(value = "parentId", required = false) Integer parentId,
                                  @RequestParam(value = "type", required = false) Integer type) {
        return menuService.getAllMenusWithOutSystem(menuName, parentId, type);
    }

    /**
     * 系统管理以外的所有菜单
     *
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<Menu> getRoleMenus(@RequestParam("roleId") Integer roleId,
                                   @RequestParam(value = "type", required = false) Integer type) {
        return menuService.getMenusByRole(roleId, type);
    }

    /**
     * 系统管理以外的所有菜单
     *
     * @return
     */
    @RequestMapping(value = "/addrolemenu", method = RequestMethod.POST)
    public APIResponse addRoleMenu(@RequestParam("roleId") Integer roleId, @RequestParam("menuIds") String menuIds) {
        try {
            if (!StringUtil.isEmpty(menuIds)) {
                String[] menuIdArr = menuIds.split(",");
                menuService.insertRoleMenu(roleId, menuIdArr);
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_UPDATE_ERROR);
        }
        return APIResponse.toOkResponse();
    }
}
