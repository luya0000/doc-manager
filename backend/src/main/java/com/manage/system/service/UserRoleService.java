package com.manage.system.service;

import com.manage.system.dao.RoleMapper;
import com.manage.system.dao.UserMapper;
import com.manage.system.dao.UserRoleMapper;
import com.manage.system.model.SysUserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Service
public class UserRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UserMapper userMapper;

    public List<Integer> getRolesByUserId(String userId) {
        List<SysUserRoleDto> roleGroupDtos = userRoleMapper.selectByParam(userId, null, null);
        List<Integer> roles = new ArrayList<>();
        if (roleGroupDtos != null && roleGroupDtos.size() > 0) {
            for (SysUserRoleDto dto : roleGroupDtos) {
                roles.add(dto.getRoleId());
            }
        }
        return roles != null ? roles : Collections.<Integer>emptyList();
    }

    /*根据用户id取得信息列表*/
    public List<SysUserRoleDto> getRolesGroupByUserId(String userId) {
        List<SysUserRoleDto> roleGroupDtos = userRoleMapper.selectByParam(userId, null, null);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysUserRoleDto>emptyList();
    }

    /*根据角色id取得信息列表*/
    public List<SysUserRoleDto> getRolesGroupByRoleId(Integer roleId) {
        List<SysUserRoleDto> roleGroupDtos = userRoleMapper.selectByParam(null, roleId, null);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysUserRoleDto>emptyList();
    }

    /*根据小组id取得信息列表*/
    public List<SysUserRoleDto> getRolesGroupByGroupId(Integer groupId) {
        List<SysUserRoleDto> roleGroupDtos = userRoleMapper.selectByParam(null, null, groupId);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysUserRoleDto>emptyList();
    }
}
