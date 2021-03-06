package com.sonia.dao;

import com.sonia.pojo.User;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class Mytest {
    @Test
    public void getUserTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> user = mapper.getUser();
        for (User user1 : user) {
            System.out.println(user1);
        }

        sqlSession.close();
    }

    @Test
    public void getUserByIdTest()  {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(2);
        System.out.println(userById);
        sqlSession.close();
    }


    @Test
    //增删改需要提交事物,不提交事物没有办法成功
    public void insertUserTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int zzzz = mapper.insertUser(new User(4, "猪猪", "123456"));
        /*注意哈:这里返回的是影响的行数*/
        System.out.println(zzzz);
        //提交事务(非常非常重要!)
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void updateUserTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int sony = mapper.updateUser(new User(1, "sonia", "888"));
        System.out.println(sony);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteUserTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.deleteUser(5);
        System.out.println(i);
        sqlSession.commit();
        sqlSession.close();
    }
}
