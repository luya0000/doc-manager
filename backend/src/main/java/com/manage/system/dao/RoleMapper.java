package com.manage.system.dao;

import com.manage.system.model.SysRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Mapper
public interface RoleMapper {

    List<SysRoleDto> selectAll(@Param("name") String name, @Param("status") Integer status) throws Exception;

    int insert(SysRoleDto user) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;

    SysRoleDto selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKey(SysRoleDto user) throws Exception;
}
