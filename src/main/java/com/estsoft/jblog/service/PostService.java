package com.estsoft.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.PostDao;
import com.estsoft.jblog.vo.CategoryVo;
import com.estsoft.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostDao dao;

	public List<PostVo> get(Long category_id) {
		List<PostVo> list = dao.getList(category_id);
		return list;
	}
}
