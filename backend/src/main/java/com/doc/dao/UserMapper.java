package com.doc.dao;

import com.doc.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDto> selectAll(@Param("account") String account, @Param("name") String name, @Param("status") Integer status);

    int insert(UserDto user);

    int deleteByPrimaryKey(Integer id);

    UserDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(UserDto user);

}
