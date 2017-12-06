package com.chillax.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	
	@RequestMapping("/redirect")
	public String gotoPage(HttpServletRequest request,@RequestParam("page")String page){
		return page;
	}

}
