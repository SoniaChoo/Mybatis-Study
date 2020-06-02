package com.sonia.Mytest;

import com.sonia.dao.TeacherMapper;
import com.sonia.pojo.Teacher;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class Mytest {
    @Test
    public void getTeacherTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
}
