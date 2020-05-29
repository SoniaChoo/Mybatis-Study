package com.sonia.Mytest;

import com.sonia.dao.UserMapper;
import com.sonia.pojo.User;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
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
        User userById = mapper.getUserById(2);
        System.out.println(userById);
        sqlSession.close();
    }

    @Test
    public void log4jTest() {
        logger.info("info:进入了log4Test");
        logger.debug("debug:进入了log4Test");
        logger.error("error:进入了log4Test");
    }

    @Test
    public void limitTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("startIndex",1);
        map.put("pageSize",2);
        List<User> userByLimit = mapper.getUserByLimit(map);
        for (User user : userByLimit) {
            System.out.println(userByLimit);
        }
        sqlSession.close();
    }

    @Test
    public void rowboundsTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        //RowBounds
        RowBounds rowBounds = new RowBounds(1, 2);
        List<User> user = sqlSession.selectList("com.sonia.dao.UserMapper.getUserByRowbounds", null, rowBounds);
        for (User user1 : user) {
            System.out.println(user1);
        }
        sqlSession.close();
    }
}
