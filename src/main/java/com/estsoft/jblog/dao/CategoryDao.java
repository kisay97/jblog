package com.estsoft.jblog.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.BlogUserVo;
import com.estsoft.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public List<CategoryVo> getList(Long blog_id) {
		List<CategoryVo> list = sqlSession.selectList("category.get_BLOG_ID", blog_id);
		return list;
	}
}
