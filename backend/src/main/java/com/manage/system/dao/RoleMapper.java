package com.manage.system.dao;

import com.manage.system.model.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luya on 2018/6/8.
 */
@Mapper
public interface RoleMapper {

    List<RoleDto> selectAll( @Param("name") String name, @Param("status") String status) throws Exception;

    int insert(RoleDto user) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;

    RoleDto selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKey(RoleDto user) throws Exception;
}
