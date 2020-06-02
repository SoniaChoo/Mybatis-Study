package com.sonia.dao;

import com.sonia.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    Teacher getTeacher(@Param("id") int id);
}
