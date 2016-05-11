package com.estsoft.jblog.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public void insert(BlogUserVo vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_id", vo.getNo());
		map.put("name", "미분류");
		map.put("description", "미분류 카테고리입니다.");
		
		sqlSession.insert("category.insert_BLOGUSERVO", map);
	}

	public int delete(Long no) {
		int deleteNum = sqlSession.delete("category.delete_NO", no);
		return deleteNum;
	}

	public CategoryVo insert(CategoryVo vo) {
		sqlSession.insert("category.insert_VO", vo);
		return vo;
	}

	public CategoryVo getByNo(Long categoryNo) {
		CategoryVo returnVo = sqlSession.selectOne("category.get_NO", categoryNo);
		return returnVo;
	}
}
