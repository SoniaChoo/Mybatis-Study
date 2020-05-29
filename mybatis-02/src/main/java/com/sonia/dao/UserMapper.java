package com.sonia.dao;


import com.sonia.pojo.User;

import java.util.List;

public interface UserMapper {
     //查询全部用户
     List<User> getUser();

     //根据id查询
     User getUserById(int id);


     //insert一个用户
     int insertUser(User user);

     //update一个用户
     int updateUser(User user);

     //delete一个用户
     int deleteUser(int id);


}
