package com.estsoft.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.jblog.annotation.AuthUser;
import com.estsoft.jblog.service.BlogService;
import com.estsoft.jblog.service.CategoryService;
import com.estsoft.jblog.service.PostService;
import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.BlogUserVo;
import com.estsoft.jblog.vo.BlogVo;
import com.estsoft.jblog.vo.CategoryVo;
import com.estsoft.jblog.vo.PostVo;

@Controller
@RequestMapping("/blog/{id}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("")
	@ResponseBody
	public ModelAndView /*String*/ enterBlog(@PathVariable("id") String id){
		ModelAndView mav = new ModelAndView();
		
		BlogUserVo user = userService.get(id);
		if(user == null){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		BlogVo blog = blogService.get(user.getNo());
		if(blog == null){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		List<CategoryVo> categoryList = categoryService.get(blog.getBlogId());
		if(categoryList == null){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		CategoryVo categoryVo = categoryList.get(0);		
		Long categoryNo = categoryVo.getNo();		
		List<PostVo> postList = postService.get( categoryNo );
		if(postList == null){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		mav.addObject("categoryList", categoryList);
		mav.addObject("postList", postList);
		mav.addObject("blogInfo", blog);
		mav.addObject("userInfo", user);
		mav.setViewName("/blog/blog-main");
		
		return mav;
	}
}
