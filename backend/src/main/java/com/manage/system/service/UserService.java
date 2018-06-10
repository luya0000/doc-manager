package com.manage.system.service;

import com.manage.common.Constants;
import com.manage.system.bean.UserBean;
import com.manage.system.dao.UserMapper;
import com.manage.system.dao.UserRoleMapper;
import com.manage.system.model.UserDto;
import com.manage.system.model.UserRoleGroupDto;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
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
public class UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    /*获取用户带权限列表*/
    @Transactional(readOnly = true)
    public List<UserBean> getUserList(String account, String name, String status) throws Exception {
        // 设置模糊查询参数
        account = StringUtils.isBlank(account) ? null : "%" + account.trim() + "%";
        String userName = StringUtils.isBlank(name) ? null : "%" + name.trim() + "%";
        status = status == null ? Constants.STATUE_INVALID : status;
        List<UserDto> userDtos = userMapper.selectAll(account, userName, status);
        List<UserBean> userList = new ArrayList<>();
        if (userDtos != null) {
            for (UserDto dto : userDtos) {
                UserBean bean = new UserBean();
                BeanUtils.copyProperties(dto, bean);
                List<String> roles = userRoleService.getRolesByUserId(bean.getId());
                bean.setRoles(roles);
                userList.add(bean);
            }
        }
        return userList;
    }

    @Transactional(readOnly = true)
    public UserBean selectByPrimaryKey(Integer userId) throws Exception {
        UserDto userDto = userMapper.selectByPrimaryKey(userId);
        UserBean userBean = new UserBean();
        if (userDto != null) {
            BeanUtils.copyProperties(userDto, userBean);
        }
        return userBean;
    }

    /*获取用户带权限*/
    @Transactional(readOnly = true)
    public UserBean selectUserAndRoleByAccount(String account) throws Exception {
        UserBean bean = new UserBean();
        UserDto userDto = userMapper.selectByAccount(account, Constants.STATUE_INVALID);
        if (userDto != null) {
            BeanUtils.copyProperties(userDto, bean);
            List<UserRoleGroupDto> roleGroupDtos = userRoleMapper.selectByParam(bean.getId(), null, null);
            if (roleGroupDtos != null && roleGroupDtos.size() > 0) {
                bean.setGroup(roleGroupDtos.get(0).getGroupId());
            }
            List<String> roles = userRoleService.getRolesByUserId(bean.getId());
            bean.setRoles(roles);
        }
        return bean;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertUser(UserBean userBean) throws Exception {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userBean, userDto);
        userDto.setPassword(BCrypt.hashpw(userBean.getPassword(), BCrypt.gensalt()));
        userDto.setStatus(Constants.STATUE_INVALID);
        userDto.setCreateUser("SYSTEM");
        userDto.setUpdateUser("SYSTEM");
        return userMapper.insert(userDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUser(UserBean userBean) throws Exception {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userBean, userDto);
        userDto.setPassword(null);
        return userMapper.updateByPrimaryKey(userDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteUser(Integer userId) throws Exception {
        return userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 修改密码,self为true修改自己密码，否则修改他人密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param self
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(Integer userId, String oldPassword, String newPassword, boolean self) throws Exception {
        UserBean user = selectByPrimaryKey(userId);
        if (self) {
            if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                return userMapper.changePassword(userId, BCrypt.hashpw(newPassword, BCrypt.gensalt())) == 1;
            }
        } else {
            return userMapper.changePassword(userId, BCrypt.hashpw(newPassword, BCrypt.gensalt())) == 1;
        }
        return false;
    }
}
