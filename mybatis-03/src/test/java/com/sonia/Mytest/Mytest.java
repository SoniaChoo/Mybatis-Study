package com.sonia.Mytest;

import com.sonia.dao.UserMapper;
import com.sonia.pojo.User;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class Mytest {

    @Test
    public void getUserByIdTest()  {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(2);
        System.out.println(userById);
        sqlSession.close();
    }

}
