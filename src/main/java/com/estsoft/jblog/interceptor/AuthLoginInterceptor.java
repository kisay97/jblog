package com.estsoft.jblog.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.BlogUserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception{
		String id = request.getParameter("id");
		String passwd = request.getParameter("password");
		
		BlogUserVo vo = new BlogUserVo();
		vo.setId(id);
		vo.setPasswd(passwd);
		
		BlogUserVo authUser = userService.login(vo);
		if(authUser==null){
			request.setAttribute("loginfail", true);
			RequestDispatcher rd = request.getRequestDispatcher(  "/WEB-INF/views/user/login.jsp"  );
			rd.forward( request, response );
			return false;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
	}
}
