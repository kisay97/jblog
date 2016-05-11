package com.estsoft.jblog.controller;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.jblog.annotation.Auth;
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
	private static final String SAVE_PATH = "/temp";
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("")
//	@ResponseBody
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
		if(categoryList == null || categoryList.isEmpty()){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		CategoryVo categoryVo = categoryList.get(0);		
		Long categoryNo = categoryVo.getNo();		
		List<PostVo> postList = postService.get( categoryNo );
		
		PostVo post;
		if(postList == null || postList.isEmpty()){
			post = null;
		}else{
			post = postList.get(0);
		}
		
		mav.addObject("id", id);
		mav.addObject("categoryList", categoryList);
		mav.addObject("postList", postList);
		mav.addObject("blogInfo", blog);
		mav.addObject("post", post);
		mav.addObject("category", categoryVo);
		mav.setViewName("/blog/blog-main");
		
		return mav;
	}
	
	@RequestMapping("/{category}")
	public ModelAndView category(
			@PathVariable("id") String id, 
			@PathVariable("category") String categoryName, 
			@RequestParam(value="no", required=true, defaultValue="1") Long postNo
	){
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
		if(categoryList == null || categoryList.isEmpty()){
			mav.setViewName("/blog/blog-not-found");
			return mav;
		}
		
		Map<String, CategoryVo> categoryMap = new HashMap<>();
		for (CategoryVo categoryVo : categoryList) {
			categoryMap.put(categoryVo.getName(), categoryVo);
		}
		
		CategoryVo categoryVo = categoryMap.get(categoryName);
		Long categoryNo = categoryVo.getNo();		
		
		List<PostVo> postList = postService.get( categoryNo );
		Map<Long, PostVo> postmap = new HashMap<>();
		for (PostVo postVo : postList) {
			postmap.put(postVo.getPostId(), postVo);
		}
		
		PostVo post;
		if(postList == null || postList.isEmpty()){
			post = null;
		}else{
			post = postmap.get(postNo);
		}
		
		mav.addObject("id", id);
		mav.addObject("categoryList", categoryList);
		mav.addObject("postList", postList);
		mav.addObject("blogInfo", blog);
		mav.addObject("post", post);
		mav.addObject("category", categoryVo);
		mav.setViewName("/blog/blog-main");
		
		return mav;
	}
	
	@Auth
	@RequestMapping("/post-write-form")
	public ModelAndView postWriteFrom(
			@PathVariable("id") String id,
			@AuthUser BlogUserVo adminUser
	){
		ModelAndView mav = new ModelAndView();
		
		if( !( id.equals(adminUser.getId()) ) ){
			mav.setViewName("/user/login");
			return mav;
		}
		
		BlogUserVo blogUser = userService.get(id);
		Long blog_id = blogUser.getNo();
		BlogVo blog = blogService.get(blog_id);
		
		List<CategoryVo> categoryList = categoryService.get(blog.getBlogId());
		
		mav.addObject("id", id);
		mav.addObject("blogInfo", blog);
		mav.addObject("categoryList", categoryList);
		mav.setViewName("/blog/blog-admin-write");
		return mav;
	}
	
	@Auth
	@RequestMapping("/postWrite")
	public String postWrite(
			@PathVariable("id") String id,
			@Valid @ModelAttribute PostVo vo,
			BindingResult result, Model model,
			@AuthUser BlogUserVo adminUser
	){
		if( !( id.equals(adminUser.getId()) ) ){
			return "redirect:/user/login";
		}
		//에러검사
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError e : list) {
				System.out.println("objectError : " + e);
			}
			model.addAllAttributes(result.getModel());
			
			BlogUserVo user = userService.get(id);
			BlogVo blog = blogService.get(user.getNo());
			List<CategoryVo> categoryList = categoryService.get(blog.getBlogId());
			model.addAttribute("id", id);
			model.addAttribute("blogInfo", blog);
			model.addAttribute("categoryList", categoryList);
			
			return "/blog/blog-admin-write";
		}
		
		//에러가 없다면 글쓰기
		Long no = postService.insert(vo);
		CategoryVo categoryVo = categoryService.getByNo(vo.getCategoryId());
		
		return "redirect:/blog/"+id+"/"+categoryVo.getName()+"?no="+no;
	}
	
	@Auth
	@RequestMapping("/blog-admin-basic")
	public ModelAndView blogAdminBasic(
			@PathVariable("id") String id,
			@AuthUser BlogUserVo adminUser
	){
		ModelAndView mav = new ModelAndView();
		
		if( !( id.equals(adminUser.getId()) ) ){
			mav.setViewName("/user/login");
			return mav;
		}
		
		BlogUserVo blogUser = userService.get(id);
		Long blog_id = blogUser.getNo();
		BlogVo blog = blogService.get(blog_id);
		
		mav.addObject("id", id);
		mav.addObject("blogInfo", blog);
		mav.setViewName("/blog/blog-admin-basic");
		
		return mav;
	}
	
	@Auth
	@RequestMapping("/blog-admin-category")
	public ModelAndView blogAdminCategory(
			@PathVariable("id") String id,
			@AuthUser BlogUserVo adminUser
	){
		ModelAndView mav = new ModelAndView();
		
		if( !( id.equals(adminUser.getId()) ) ){
			mav.setViewName("/user/login");
			return mav;
		}
		
		BlogUserVo blogUser = userService.get(id);
		Long blog_id = blogUser.getNo();
		BlogVo blog = blogService.get(blog_id);
		
		List<CategoryVo> categoryList = categoryService.get(blog.getBlogId());
		if(categoryList == null || categoryList.isEmpty()){
			mav.addObject("categoryList", null);
			return mav;
		}else{
			mav.addObject("categoryList", categoryList);			
		}
		
		mav.addObject("id", id);
		mav.addObject("blogInfo", blog);
		mav.setViewName("/blog/blog-admin-category");
		
		return mav;
	}
	
	@Auth
	@RequestMapping("/ajax-fileUpload")
	@ResponseBody
	public Map<String, Object> ajaxList(
			@PathVariable("id") String id,
			MultipartHttpServletRequest request,
			@AuthUser BlogUserVo adminUser
	){
		System.out.println(request);
		
		if( !( id.equals(adminUser.getId()) ) ){
			return new HashMap<String, Object>();
		}
		
		Iterator<String> itr = request.getFileNames();
		Map<String, Object> map = new HashMap<String, Object>();
		if(itr.hasNext()){
			//fileUpload
			MultipartFile mpf = request.getFile(itr.next());
			if(mpf.isEmpty() == false){
				String fileOriginalName = mpf.getOriginalFilename();
				String extName = fileOriginalName.substring( fileOriginalName.lastIndexOf(".") + 1, fileOriginalName.length() );
		        String saveFileName = genSaveFileName( extName );
		        System.out.println(SAVE_PATH + "/" + saveFileName);
		        writeFile(mpf, SAVE_PATH, saveFileName);
		        
		        map.put("result", "success");
		        map.put("data", "/assets/user_image/" + saveFileName);
		        
		        //upload database
		        BlogUserVo user = userService.get(id);
				blogService.updateLogo(user.getNo(), saveFileName);
			}
			
			//return map
			return map;
		}else{	
			return map;
		}
	}
	
	@Auth
	@RequestMapping("/admin-basic-submit")
	@ResponseBody
	public Map<String, Object> adminBasicSubmit(
			@PathVariable("id") String id,
			@RequestParam(value="title", required=true, defaultValue="'s blog") String title,
			@AuthUser BlogUserVo adminUser
	){
		if( !( id.equals(adminUser.getId()) ) ){
			return new HashMap<String, Object>();
		}
		
		if(title.equals("'s blog")) title = id+title;
		
		BlogUserVo user = userService.get(id);
		blogService.updateTitle(user.getNo(), title);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("result", "success");
		map.put("title", title);
		
		return map;
	}
	
	@Auth
	@RequestMapping("/Ajax-deleteCategory")
	@ResponseBody
	public Map<String, Object> AjaxDeleteCategory(
			@PathVariable("id") String id,
			@RequestParam("categoryNo") Long no,
			@AuthUser BlogUserVo adminUser
	){
		if( !( id.equals(adminUser.getId()) ) ){
			return new HashMap<String, Object>();
		}
		
		Map<String, Object> map = new HashMap<>();
		
		BlogUserVo user = userService.get(id);
		BlogVo blog = blogService.get(user.getNo());
		List<CategoryVo> categoryList = categoryService.get(blog.getBlogId());
		if(categoryList == null || categoryList.isEmpty()){
			map.put("result", -1);
			return map;
		}
		
		Map<Long, CategoryVo> categoryMap = new HashMap<>();
		for (CategoryVo categoryVo : categoryList) {
			categoryMap.put(categoryVo.getNo(), categoryVo);
		}
		
		CategoryVo vo = categoryMap.get(no);
		if(vo.getPostCount() > 0){
			map.put("result", -2);
			return map;
		}
		
		int state = categoryService.delete(no);
		if(state < 1){
			map.put("result", -1);
			return map;
		}
		
		map.put("result", 1);		
		return map;
	}
	
	@Auth
	@RequestMapping("/Ajax-insertCategory")
	@ResponseBody
	public Map<String, Object> AjaxInsertCategory(
		@PathVariable("id") String id,
		@RequestParam("name") String name,
		@RequestParam("disc") String discription,
		@AuthUser BlogUserVo adminUser
	){
		if( !( id.equals(adminUser.getId()) ) ){
			return new HashMap<String, Object>();
		}
		
		Map<String, Object> map = new HashMap<>();
		
		BlogUserVo user = userService.get(id);
		BlogVo blog = blogService.get(user.getNo());
		
		CategoryVo vo = new CategoryVo();
		vo.setBlog_id(blog.getBlogId());
		vo.setName(name);
		vo.setDiscription(discription);
		vo.setPostCount(0L);
		
		map.put("result", "success");
		map.put("data", categoryService.insert(vo));
		
		return map;
	}
	
	@Auth
	@RequestMapping("/deletePost")
	public String deletePost(
			@AuthUser BlogUserVo adminUser,
			@PathVariable("id") String id,
			@RequestParam(value="no", required=true, defaultValue="1") Long postId
	){
		if(!( id.equals(adminUser.getId()) )) return "redirect:/user/login";
		
		postService.delete(postId);
		
		return "redirect:/blog/"+id;
	}
	
	private void writeFile( MultipartFile file, String path, String fileName ) {
		FileOutputStream fos = null;
		try {
			byte fileData[] = file.getBytes();
			fos = new FileOutputStream( path + "/" + fileName );
			fos.write(fileData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	private String genSaveFileName( String extName ) {
		
        Calendar calendar = Calendar.getInstance();                                                                                                                                                                                               
		String fileName = "";
        
        fileName += calendar.get( Calendar.YEAR );
        fileName += calendar.get( Calendar.MONTH );
        fileName += calendar.get( Calendar.DATE );
        fileName += calendar.get( Calendar.HOUR );
        fileName += calendar.get( Calendar.MINUTE );
        fileName += calendar.get( Calendar.SECOND );
        fileName += calendar.get( Calendar.MILLISECOND );
        fileName += ( "." + extName );
        
        return fileName;
	}
}