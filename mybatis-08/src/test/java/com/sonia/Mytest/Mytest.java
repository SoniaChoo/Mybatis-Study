package com.sonia.Mytest;

import com.sonia.dao.BlogMapper;
import com.sonia.pojo.Blog;
import com.sonia.util.IDUtil;
import com.sonia.util.UserUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Mytest {
    @Test
    public void insertBlogTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setId(IDUtil.genId());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("狂神说");
        blog.setCreateTime(new Date());
        blog.setViews(9999);

        mapper.insertBlog(blog);

        blog.setId(IDUtil.genId());
        blog.setTitle("Java如此简单");
        mapper.insertBlog(blog);

        blog.setId(IDUtil.genId());
        blog.setTitle("Spring如此简单");
        mapper.insertBlog(blog);

        blog.setId(IDUtil.genId());
        blog.setTitle("微服务如此简单");
        mapper.insertBlog(blog);
        sqlSession.close();

    }

    @Test
    public void selectBlogTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap<String, String> map = new HashMap<String, String>();
        //map.put("author","狂神说");
        map.put("title","Java如此简单");
        List<Blog> blogs = mapper.selectBlogIf(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }

    @Test
    public void selectBlogChooseTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap<String, String> map = new HashMap<String, String>();
        //map.put("author","狂神说");
        //map.put("title","Java如此简单");
        map.put("views","9999");
        List<Blog> blogs = mapper.selectBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }

    @Test
    public void selectBlogForEachTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        //ids.add(2);
        map.put("ids",ids);
        List<Blog> blogs = mapper.selectBlogForEach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }

}
