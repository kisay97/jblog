package com.estsoft.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.BlogDao;
import com.estsoft.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogDao dao;

	public BlogVo get(Long blog_id) {
		BlogVo vo = dao.get(blog_id);
		return vo;
	}
}
