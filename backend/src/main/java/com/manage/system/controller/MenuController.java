package com.manage.system.controller;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = UrlConstants.URL_DOC_MODEL)
public class MenuController extends BaseController{

    Log logger = LogFactory.getLog(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * get menus
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<Menu> getUserMenus(HttpServletRequest request) {
        Integer useId = super.getUserId();
        return menuService.generateMenus(useId);
    }

}
