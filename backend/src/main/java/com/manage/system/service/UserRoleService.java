package com.manage.system.service;

import com.manage.system.dao.RoleMapper;
import com.manage.system.dao.UserMapper;
import com.manage.system.dao.UserRoleMapper;
import com.manage.system.model.UserRoleGroupDto;
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

    public List<String> getRolesByUserId(Integer userId) {
        List<UserRoleGroupDto> roleGroupDtos = userRoleMapper.selectByParam(userId, null, null);
        List<String> roles = new ArrayList<>();
        if (roleGroupDtos != null && roleGroupDtos.size() > 0) {
            for(UserRoleGroupDto dto : roleGroupDtos){
                roles.add(dto.getRoleType());
            }
        }
        return roles != null ? roles : Collections.<String>emptyList();
    }
}
