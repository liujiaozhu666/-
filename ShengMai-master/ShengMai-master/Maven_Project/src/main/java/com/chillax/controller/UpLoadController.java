package com.chillax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.chillax.util.Upload;

@Controller
public class UpLoadController {

	@RequestMapping("/upLoadShopLogo")
	public String upLoadShopLogo(MultipartFile file,HttpServletRequest request, HttpServletResponse response){
		Upload up = new Upload();
		try {
			String name=up.uploadPic(request, response, request.getServletContext());
			System.out.println("Í¼Æ¬Â·¾¶----------------"+name);
			request.setAttribute("path", name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "addshops";
		
	}
}
