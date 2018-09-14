package com.manage.system.service;

import com.manage.common.Constants;
import com.manage.system.bean.RoleBean;
import com.manage.system.dao.RoleMapper;
import com.manage.system.model.SysRoleDto;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /*获取角色列表*/
    @Transactional(readOnly = true)
    public List<RoleBean> getRoleList(String name, Integer status) throws Exception {
        // 设置模糊查询参数
        String roleName = StringUtils.isBlank(name) ? null : "%" + name.trim() + "%";
       // status = status == null ? Constants.STATUE_INVALID : status;
        List<SysRoleDto> roleDtos = roleMapper.selectAll(roleName, status);
        List<RoleBean> roleList = new ArrayList<>();
        if (roleDtos != null) {
            for (SysRoleDto dto : roleDtos) {
                RoleBean bean = new RoleBean();
                BeanUtils.copyProperties(dto, bean);
                roleList.add(bean);
            }
        }
        return roleList;
    }

    @Transactional(readOnly = true)
    public RoleBean selectByPrimaryKey(Integer userId) throws Exception {
        RoleBean bean = new RoleBean();
        SysRoleDto dto = roleMapper.selectByPrimaryKey(userId);
        if (dto != null) {
            BeanUtils.copyProperties(dto, bean);
        }
        return bean;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertRole(RoleBean roleBean) throws Exception {
        SysRoleDto roleDto = new SysRoleDto();
        BeanUtils.copyProperties(roleBean, roleDto);
        //roleDto.setStatus(Constants.STATUE_INVALID);
        return roleMapper.insert(roleDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(RoleBean roleBean) throws Exception {

        SysRoleDto roleDto = new SysRoleDto();
        BeanUtils.copyProperties(roleBean, roleDto);
       // roleDto.setStatus(Constants.STATUE_INVALID);
        return roleMapper.updateByPrimaryKey(roleDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer userId) throws Exception {
        return roleMapper.deleteByPrimaryKey(userId);
    }
}
