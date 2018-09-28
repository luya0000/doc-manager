package com.manage.system.service;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.manage.system.dao.RoleMapper;
import com.manage.system.dao.UserMapper;
import com.manage.system.dao.UserRoleMapper;
import com.manage.system.model.SysRoleDto;
import com.manage.system.model.SysUserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(readOnly = true)
    public List<Integer> getRolesIdByParam(String userId, Integer roleId) {
        List<SysRoleDto> roleList = userRoleMapper.selectByParam(userId, roleId);
        List<Integer> roles = new ArrayList<>();
        if (roleList != null && roleList.size() > 0) {
            for (SysRoleDto dto : roleList) {
                roles.add(dto.getId());
            }
        }
        return roles != null ? roles : Collections.<Integer>emptyList();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delUserRoleByUserId(String userId, Integer roleId) {
        int result = userRoleMapper.deleteByParam(userId, roleId);
        return result;
    }
}
