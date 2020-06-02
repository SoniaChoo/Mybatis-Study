package com.sonia.dao;

import com.sonia.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

     //根据id查询
     @Select("select * from user")
     List<User> getUser();

     @Select("select * from user where id= #{id}")
     User getUserById(@Param("id") int id);

     @Insert("insert into user(id,name,pwd) values (#{id}, #{name}, #{pwd})")
     int addUser(User user);

     @Update("update user set name=#{name}, pwd=#{pwd} where id=#{id}")
     int updateUser(User user);

     @Delete("delete from user where id = #{uid}")
     int deleteUser(@Param("uid") int id);
}
