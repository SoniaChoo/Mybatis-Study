package com.sonia.dao;

import com.sonia.pojo.Student;

import java.util.List;

public interface StudentMapper {
    //查询所有学生的信息,以及对应老师的信息
    List<Student> getStudent();
}
