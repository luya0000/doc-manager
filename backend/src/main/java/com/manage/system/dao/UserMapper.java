package com.manage.system.dao;

import com.manage.system.model.SysUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<SysUserDto> selectAll(@Param("userId") String userId, @Param("name") String name, @Param("status") Integer status) throws Exception;

    SysUserDto selectByUserId(@Param("userId") String userId,@Param("status") Integer status) throws Exception;

    int insert(SysUserDto user) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;

    SysUserDto selectByPrimaryKey(@Param("userId") Integer userId) throws Exception;

    int updateByPrimaryKey(SysUserDto user) throws Exception;

    int changePassword(@Param("userId") Integer userId, @Param("password")String newPassword);

}
