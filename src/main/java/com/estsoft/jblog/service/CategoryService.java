package com.estsoft.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.CategoryDao;
import com.estsoft.jblog.vo.BlogUserVo;
import com.estsoft.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao dao;

	public List<CategoryVo> get(Long blog_id) {
		List<CategoryVo> list = dao.getList(blog_id);
		return list;
	}

	public void insert(BlogUserVo vo) {		
		dao.insert(vo);
	}

	public int delete(Long no) {
		return dao.delete(no);
	}

	public CategoryVo insert(CategoryVo vo) {
		return dao.insert(vo);
	}

	public CategoryVo getByNo(Long categoryNo) {
		return dao.getByNo(categoryNo);
	}
}