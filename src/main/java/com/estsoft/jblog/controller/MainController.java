package com.estsoft.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping(value={"/main",""})
	public String index(){
		System.out.println("com.estsoft.jblog.controller.MainController.java : 들어왔당!");
		return "/main/index";
	}
}
