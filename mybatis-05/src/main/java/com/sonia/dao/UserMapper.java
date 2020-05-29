package com.sonia.dao;

import com.sonia.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

     //根据id查询
     @Select("select * from user")
     List<User> getUser();

     @Select("select * from user where id= #{id}")
     User getUserById(@Param("id") int id);

}
