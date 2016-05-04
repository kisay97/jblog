package com.estsoft.jblog.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.BlogUserVo;

@Repository
public class BlogUserDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public BlogUserVo get(BlogUserVo vo) {
		BlogUserVo returnVo = sqlSession.selectOne("bloguser.get_VO", vo);
		return returnVo;
	}

	public BlogUserVo get(String id) {
		BlogUserVo returnVo = sqlSession.selectOne("bloguser.get_ID", id);
		return returnVo;
	}

	public void insert(BlogUserVo vo) {
		sqlSession.insert("bloguser.insert", vo);
	}
}
