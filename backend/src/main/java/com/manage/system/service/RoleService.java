package com.manage.system.service;

import com.github.pagehelper.util.StringUtil;
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

    @Autowired
    private RolePermService rolePermService;

    /*获取角色列表*/
    @Transactional(readOnly = true)
    public List<RoleBean> getRoleList(String name, Integer departId) throws Exception {
        // 设置模糊查询参数
        String roleName = StringUtils.isBlank(name) ? null : "%" + name.trim() + "%";
        List<SysRoleDto> roleDtos = roleMapper.selectAll(roleName, departId);
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
    public RoleBean selectByPrimaryKey(Integer roleId) throws Exception {
        RoleBean bean = new RoleBean();
        SysRoleDto roleDto = roleMapper.selectByPrimaryKey(roleId);
        // 获取角色信息
        if (roleDto != null) {
            BeanUtils.copyProperties(roleDto, bean);
        }
        // 获取角色对应权限信息
        List<Integer> premList = rolePermService.getPermByParam(roleDto.getId());
        bean.setPermList(premList);
        return bean;
    }

    /**
     * 插入角色和权限
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean insertRole(RoleBean roleBean, String perms) throws Exception {
        SysRoleDto roleDto = new SysRoleDto();
        BeanUtils.copyProperties(roleBean, roleDto);
        roleMapper.insert(roleDto);
        if (!StringUtil.isEmpty(perms)) {
            String[] permIds = perms.split(",");
            for (String id : permIds) {
                rolePermService.insertRolePerm(Integer.parseInt(id), roleDto.getId());
            }
        }
        return true;
    }

    /**
     * 修改角色和权限
     *
     * @param roleBean
     * @param perms
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateByPrimaryKey(RoleBean roleBean, String perms) throws Exception {

        SysRoleDto roleDto = new SysRoleDto();
        BeanUtils.copyProperties(roleBean, roleDto);
        roleMapper.updateByPrimaryKey(roleDto);
        // 删除原有角色权限关系
        rolePermService.deleteByPrimaryKey(null, roleBean.getId());
        if (!StringUtil.isEmpty(perms)) {
            String[] permIds = perms.split(",");
            for (String id : permIds) {
                rolePermService.insertRolePerm(Integer.parseInt(id), roleDto.getId());
            }
        }
        return true;
    }

    /**
     * 删除角色 删除对应权限
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer roleId) throws Exception {
        SysRoleDto roleDto = roleMapper.selectByPrimaryKey(roleId);
        rolePermService.deleteByPrimaryKey(null, roleDto.getId());
        return roleMapper.deleteByPrimaryKey(roleId, null);
    }
}
