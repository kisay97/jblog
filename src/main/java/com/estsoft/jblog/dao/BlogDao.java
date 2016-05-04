package com.estsoft.jblog.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.BlogVo;

@Repository
public class BlogDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public BlogVo get(Long blog_id) {
		BlogVo returnVo = sqlSession.selectOne("blog.get_BLOG_ID", blog_id);
		return returnVo;
	}
}
