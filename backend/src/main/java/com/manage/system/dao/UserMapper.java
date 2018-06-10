package com.manage.system.dao;

import com.manage.system.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDto> selectAll(@Param("account") String account, @Param("name") String name, @Param("status") String status) throws Exception;

    UserDto selectByAccount(@Param("account") String account,@Param("status") String status) throws Exception;

    int insert(UserDto user) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;

    UserDto selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKey(UserDto user) throws Exception;

    int changePassword(@Param("id") Integer userId, @Param("password")String newPassword);

}
