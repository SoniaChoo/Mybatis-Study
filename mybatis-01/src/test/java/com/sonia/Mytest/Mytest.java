package com.sonia.Mytest;

import com.sonia.dao.UserDao;
import com.sonia.user.User;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.List;

public class Mytest {
    @Test
    public void test(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> user = mapper.getUser();
        for (User user1 : user) {
            System.out.println(user1);
        }

        sqlSession.close();
    }
}
