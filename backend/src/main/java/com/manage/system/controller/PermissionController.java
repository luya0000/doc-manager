package com.manage.system.controller;

import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.UrlConstants;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.system.model.PermissionDto;
import com.manage.system.service.PermissionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luya on 2018/6/8.
 * 权限内容初始化时加入，此类用于后期单独对权限维护，不参与前端操作
 */
@RestController
@RequestMapping(value = UrlConstants.URL_PERM_MODEL)
public class PermissionController extends BaseController {

    Log logger = LogFactory.getLog(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表
     *
     * @return
     */
    @GetMapping("/list")
    public APIResponse permissionList() {

        List<PermissionDto> permissionList = null;
        try {
            permissionList = permissionService.getPermissionList();
            return APIResponse.toOkResponse(permissionList);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.PERMIS_SELETE_ERROR);
        }
    }

    /**
     * 添加权限
     *
     * @param name
     * @param type
     * @return
     */
    @PostMapping("/add")
    public APIResponse addPermission(
            @RequestParam("name") String name,
            @RequestParam("type") String type) {

        PermissionDto permission = new PermissionDto();
        permission.setName(name);
        permission.setType(type);
        permission.setCreateUser(getUserName());
        permission.setUpdateUser(getUserName());
        try {
            permissionService.insertPermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.PERMIS_ADD_ERROR);
        }
        return APIResponse.toOkResponse();
    }


    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public APIResponse deletePermission(@PathVariable(value = "id") Integer id) {

        try {
            permissionService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.PERMIS_DELETE_ERROR);
        }
        return APIResponse.toOkResponse();
    }
}
