package com.estsoft.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.CategoryDao;
import com.estsoft.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao dao;

	public List<CategoryVo> get(Long blog_id) {
		List<CategoryVo> list = dao.getList(blog_id);
		return list;
	}
	
	
}