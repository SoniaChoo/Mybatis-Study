package com.sonia.dao;

import com.sonia.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper {
    //获取老师
   /* List<Teacher> getTeacher();*/

    //获取指定老师下的所有学生以及老师的信息
    Teacher getTeacher(@Param("tid") int id);
}
