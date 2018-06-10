package com.manage.system.dao;

import com.manage.system.model.PermissionDto;
import com.manage.system.model.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Mapper
public interface PermissionMapper {

    List<PermissionDto> selectAll() throws Exception;

    int insert(PermissionDto user) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;

    PermissionDto selectByPrimaryKey(Integer id) throws Exception;
}
