package com.sonia.dao;

import com.sonia.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

     //根据id查询
     User getUserById(int id);

     //根据分页查找limit
     List<User> getUserByLimit(Map<String,Integer> map);

     //根据分页查找rowbounds实现
     List<User> getUserByRowbounds();
}
