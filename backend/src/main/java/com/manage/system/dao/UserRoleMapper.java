package com.manage.system.dao;

import com.manage.system.model.SysUserRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Mapper
public interface UserRoleMapper {

    public int insert(SysUserRoleDto userRoleGroupDto);

    public int deleteByPrimaryKey(Integer id);

    public int deleteByParam(@Param("userId") Integer userId, @Param("roleId") Integer roleId, @Param("groupId") Integer groupId);

    public List<SysUserRoleDto> selectByParam(@Param("userId") String userId, @Param("roleId") Integer roleId, @Param("groupId") Integer groupId);
}
