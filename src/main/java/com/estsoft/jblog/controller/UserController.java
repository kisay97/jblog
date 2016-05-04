package com.estsoft.jblog.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.jblog.annotation.AuthUser;
import com.estsoft.jblog.service.CategoryService;
import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.BlogUserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/loginform")
	public String loginform(){
		return "/user/login";
	}
	
	@RequestMapping("/joinform")
	public String joinform(){
		return "/user/join";
	}
	
	@RequestMapping("/join")
	public String join(@ModelAttribute @Valid BlogUserVo vo, BindingResult result, Model model){
		/* 에러가 있는지 검사 */
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e : list){
				System.out.println("objectError : " + e);
			}
			
			model.addAllAttributes(result.getModel());
			return "/user/join";
		}
		
		/* 에러가 없다면 회원가입 실행  */
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
}