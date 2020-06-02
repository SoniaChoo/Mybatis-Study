package com.sonia.Mytest;

import com.sonia.dao.StudentMapper;
import com.sonia.dao.TeacherMapper;
import com.sonia.pojo.Student;
import com.sonia.pojo.Teacher;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class Mytest {
    @Test
    public void getTeacherTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }

    @Test
    public void getStudentTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> student = mapper.getStudent();
        for (Student student1 : student) {
            System.out.println(student1);
        }
        sqlSession.close();
    }
}
