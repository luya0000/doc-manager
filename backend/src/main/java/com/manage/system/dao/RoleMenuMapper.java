package com.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luya on 2018/9/22.
 */
@Mapper
public interface RoleMenuMapper {

    List<Integer> selectByRoleId(@Param("roleId") Integer roleId) throws Exception;

    int insert(@Param("menuId") Integer menuId, @Param("roleId") Integer roleId) throws Exception;

    int deleteByPrimaryKey(@Param("menuId") Integer menuId, @Param("roleId") Integer roleId, @Param("menuType") Integer menuType) throws Exception;
}