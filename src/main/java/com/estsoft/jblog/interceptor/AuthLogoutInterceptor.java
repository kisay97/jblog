package com.estsoft.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.jblog.service.UserService;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		HttpSession session = request.getSession();
		if(session != null){
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath());
		System.out.println("com.estsoft.jblog.interceptor.AuthLogoutInterceptor->로그아웃 완료");
		return false; //true 컨트롤러로 보냄. false 컨트롤러로 안보냄
	}
}