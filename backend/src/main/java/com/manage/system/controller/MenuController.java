package com.manage.system.controller;

import javax.servlet.http.HttpServletRequest;

import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.UrlConstants;
import com.manage.system.bean.Menu;
import com.manage.system.service.MenuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UrlConstants.URL_MENU_MODEL)
public class MenuController extends BaseController{

    Log logger = LogFactory.getLog(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 获取用户拥有的菜单列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse getUserMenus() {
        String useId = super.getAccount();
        List<Menu> menus = menuService.getUserMenus(useId);
        return response(menus);
    }

    /**
     * 系统管理以外的所有菜单
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Menu> getAllMenus() {
        return menuService.getAllMenusWithOutSystem();
    }

}
