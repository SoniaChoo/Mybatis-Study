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
        User user = mapper.getUserById(1);
        System.out.println(user);
        sqlSession.close();
    }
}
