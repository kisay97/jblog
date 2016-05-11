package com.estsoft.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.BlogUserDao;
import com.estsoft.jblog.vo.BlogUserVo;

@Service
public class UserService {
	@Autowired
	private BlogUserDao dao;

	public BlogUserVo login(BlogUserVo vo) {
		BlogUserVo authUser = dao.get(vo);
		return authUser;
	}

	public BlogUserVo get(String id) {
		BlogUserVo user = dao.get(id);
		return user;
	}

	public Long join(BlogUserVo vo) {
		return dao.insert(vo);
	}
}
