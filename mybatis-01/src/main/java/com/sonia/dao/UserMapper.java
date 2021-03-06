package com.sonia.dao;

import com.sonia.user.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
     //查询全部用户
     List<User> getUser();

     //根据id查询
     User getUserById(int id);

     //根据map查询用户
     User getUserById2(Map map);

     //模糊查询用户

     List<User> getUserByLike(Map map);

     //insert一个用户
     int insertUser(User user);

     //以map为参数添加一个用户
     int insertUser2(Map<String,Object> map);

     //update一个用户
     int updateUser(User user);

     //delete一个用户
     int deleteUser(int id);


}
