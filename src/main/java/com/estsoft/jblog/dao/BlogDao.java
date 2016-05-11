package com.estsoft.jblog.dao;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import com.estsoft.jblog.vo.BlogUserVo;
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

	public void insert(BlogUserVo vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_id", vo.getNo());
		map.put("title", vo.getName()+"'s blog");
		map.put("logo", "spring-logo");
		
		sqlSession.insert("blog.insert_BLOGUSERVO", map);
	}

	public void updateLogo(Long id, String saveFileName) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_id", id);
		map.put("logo", saveFileName);
		sqlSession.update("blog.updateLogo_VALUE", map);
	}

	public void updateTitle(Long id, String title) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_id", id);
		map.put("title", title);
		sqlSession.update("blog.updateTitle_VALUE", map);
	}
}
