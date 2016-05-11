package com.estsoft.jblog.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.PostVo;

@Repository
public class PostDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> getList(Long category_id) {
		List<PostVo> list = sqlSession.selectList("post.get_CATEGORY_ID", category_id);
		return list;
	}

	public Long insert(PostVo vo) {
		sqlSession.insert("post.insert_VO", vo);
		return vo.getPostId();
	}

	public void delete(Long postId) {
		sqlSession.insert("post.delete_ID", postId);
	}
}
