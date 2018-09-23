package com.manage.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.Constants;
import com.manage.common.UrlConstants;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.system.bean.RoleBean;
import com.manage.system.service.RoleService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by luya on 2018/6/9.
 */
@RestController
@RequestMapping(value = UrlConstants.URL_ROLE_MODEL)
public class RoleController extends BaseController {

    private Log logger = LogFactory.getLog(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 根据条件获取角色列表
     *
     * @param currPage
     * @param pageSize
     * @param roleName
     * @return
     */
    @GetMapping("/list")
    public APIResponse roleList(@RequestParam(value = "currPage", required = false) Integer currPage,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                @RequestParam(value = "roleName", required = false) String roleName,
                                @RequestParam(value = "departId", required = false) Integer departId) {

        currPage = currPage == null ? Constants.PAGEHELPER_PAGE_CURRENT : currPage;
        pageSize = pageSize == null ? Constants.PAGEHELPER_PAGE_SIZE : pageSize;

        List<RoleBean> roleList = null;
        try {
            Page page = PageHelper.startPage(currPage, pageSize, true);
            roleList = roleService.getRoleList(roleName, departId);
            Map result = new HashedMap();
            result.put("data", roleList);
            result.put("currPage", currPage);
            result.put("total", page.getTotal());
            return APIResponse.toOkResponse(result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_SELETE_ERROR);
        }
    }

    /**
     * 根据主键获取角色信息
     *
     * @param roleId
     * @return
     */
    @GetMapping("/{id}")
    public APIResponse roleInfo(@PathVariable("id") Integer roleId) {

        try {
            RoleBean role = roleService.selectByPrimaryKey(roleId);
            return APIResponse.toOkResponse(role);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_SELETE_ERROR);
        }
    }

    /**
     * 添加角色
     *
     * @param roleName
     * @param departId
     * @param note
     * @param permistion
     * @return
     */
    @PostMapping("/add")
    public APIResponse addRole(@RequestParam("roleName") String roleName, @RequestParam("departId") Integer departId,
                               @RequestParam("note") String note, @RequestParam("permistion") String permistion) {

        RoleBean roleBean = new RoleBean();
        roleBean.setName(roleName);
        roleBean.setDepartId(departId);
        roleBean.setNote(note);
        roleBean.setUpdateUser(getUserName());
        try {
            roleService.insertRole(roleBean, permistion);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_ADD_ERROR);
        }
        return APIResponse.toOkResponse();
    }


    /**
     * 修改角色
     *
     * @param id
     * @param roleName
     * @param note
     * @param permistion
     * @return
     */
    @PostMapping("/update")
    public APIResponse updateRole(@RequestParam(value = "id") Integer id, @RequestParam("roleName") String roleName,
                                  @RequestParam("note") String note, @RequestParam("permistion") String permistion) {

        RoleBean roleBean = new RoleBean();
        roleBean.setId(id);
        roleBean.setName(roleName);
        roleBean.setNote(note);
        roleBean.setUpdateUser(getUserName());
        try {
            roleService.updateByPrimaryKey(roleBean, permistion);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_UPDATE_ERROR);
        }
        return APIResponse.toOkResponse();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public APIResponse deleteRole(@PathVariable(value = "id") Integer id) {

        try {
            roleService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.ROLE_DELETE_ERROR);
        }
        return APIResponse.toOkResponse();
    }

}
