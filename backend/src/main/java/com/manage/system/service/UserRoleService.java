package com.manage.system.service;

import com.manage.system.dao.RoleMapper;
import com.manage.system.dao.UserMapper;
import com.manage.system.dao.UserRoleMapper;
import com.manage.system.model.SysRoleDto;
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
        List<SysRoleDto> roleList = userRoleMapper.selectByParam(userId, null);
        List<Integer> roles = new ArrayList<>();
        if (roleList != null && roleList.size() > 0) {
            for (SysRoleDto dto : roleList) {
                roles.add(dto.getId());
            }
        }
        return roles != null ? roles : Collections.<Integer>emptyList();
    }

    public int delUserRoleByUserId(String userId, Integer roleId) {
        int result = userRoleMapper.deleteByParam(userId, roleId);
        return result;
    }

    /*根据用户id取得信息列表*//*
    public List<SysRoleDto> getRolesGroupByUserId(String userId) {
        List<SysRoleDto> roleGroupDtos = userRoleMapper.selectByParam(userId, null);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysRoleDto>emptyList();
    }

    *//*根据角色id取得信息列表*//*
    public List<SysRoleDto> getRolesGroupByRoleId(Integer roleId) {
        List<SysRoleDto> roleGroupDtos = userRoleMapper.selectByParam(null, roleId);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysRoleDto>emptyList();
    }

    *//*根据小组id取得信息列表*//*
    public List<SysRoleDto> getRolesGroupByGroupId(Integer groupId) {
        List<SysRoleDto> roleGroupDtos = userRoleMapper.selectByParam(null, null);
        return roleGroupDtos != null ? roleGroupDtos : Collections.<SysRoleDto>emptyList();
    }*/
}
