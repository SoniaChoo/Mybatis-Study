package com.sonia.dao;

import com.sonia.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

     //根据id查询
     User getUserById(int id);
}
