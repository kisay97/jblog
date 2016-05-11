package com.estsoft.jblog.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.jblog.annotation.Auth;
import com.estsoft.jblog.vo.BlogUserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception{
		request.getRequestURI();
		if(handler instanceof HandlerMethod == false){
			return true;
		}
		Auth auth = ( ( HandlerMethod ) handler ).getMethodAnnotation(Auth.class);
		
		//접근제어가 필요없음
		if(auth == null){
			return true;
		}
			
		//접근 제어(로그인 필요)
		HttpSession session = request.getSession();
		if(session == null){
			response.sendRedirect(request.getContextPath() + "/user/loginform");
			return false;
		}

		BlogUserVo vo = (BlogUserVo)session.getAttribute( "authUser" );
		if( vo == null ) {
			response.sendRedirect(request.getContextPath() + "/user/loginform");
			return false;			
		}
		
		String uriTokens[] = request.getRequestURI().split("/");
		
		if( !( vo.getId().equals(URLDecoder.decode(uriTokens[3])) ) ){
			response.sendRedirect(request.getContextPath() + "/user/loginform");
			return false;
		}

		return true;
	}
}
