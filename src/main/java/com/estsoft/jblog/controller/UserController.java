package com.estsoft.jblog.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.jblog.service.BlogService;
import com.estsoft.jblog.service.CategoryService;
import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.BlogUserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
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
		Long no = userService.join(vo);				//User 데이터를 넣어두고, no를 받아옴
		vo.setNo(no);								//vo에 no를 셋팅해둠
		blogService.insert(vo);						//User 데이터로 블로그를 만들어줌
		categoryService.insert(vo);					//어차피 User.no == blog_id, 따라서 유저 정보로 기본 카테고리를 만들어줌
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/checkid")
	@ResponseBody
	public Object checkEmail(@RequestParam(value="id", required=true, defaultValue="") String id){
		BlogUserVo vo = userService.get(id);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", vo==null);
		
		return map;
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess(){
		return "/user/joinsuccess";
	}
}