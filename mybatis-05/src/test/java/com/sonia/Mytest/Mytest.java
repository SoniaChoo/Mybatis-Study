package com.sonia.Mytest;

import com.sonia.dao.UserMapper;
import com.sonia.pojo.User;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


public class Mytest {

    static Logger logger = Logger.getLogger(Mytest.class);

    @Test
    public void getUserByIdTest()  {
        SqlSession sqlSession = UserUtil.getSqlSession();
        logger.info(sqlSession);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void addUserTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int huihui = mapper.addUser(new User(8, "huihui", "888999"));
        System.out.println(huihui);
        sqlSession.close();
    }

    @Test
    public void updateUserTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUser(new User(7,"hello","888"));
        sqlSession.close();
    }

    @Test
    public void deleteUserTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.deleteUser(7);
        System.out.println(i);
        sqlSession.close();
    }
}
