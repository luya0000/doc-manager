package com.manage.system.service;

import com.manage.common.Constants;
import com.manage.system.bean.RoleBean;
import com.manage.system.dao.PermissionMapper;
import com.manage.system.dao.RoleMapper;
import com.manage.system.model.PermissionDto;
import com.manage.system.model.RoleDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /*获取权限列表*/
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionList() throws Exception {
        List<PermissionDto> permissionDtoList = permissionMapper.selectAll();
        return permissionDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertPermission(PermissionDto permissionDto) throws Exception {
        permissionDto.setStatus(Constants.STATUE_INVALID);
        return permissionMapper.insert(permissionDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return permissionMapper.deleteByPrimaryKey(id);
    }
}
