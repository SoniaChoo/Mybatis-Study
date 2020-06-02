package com.sonia.dao;

import com.sonia.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //插入数据
    int insertBlog(Blog blog);

    //查找数据if
    List<Blog> selectBlogIf(Map map);

    //查找choose
    List<Blog> selectBlogChoose(Map map);

    //查询第1-2-3号记录的博客
    List<Blog> selectBlogForEach(Map map);
}
