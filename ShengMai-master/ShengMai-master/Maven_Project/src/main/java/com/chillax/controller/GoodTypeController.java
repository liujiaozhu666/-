package com.chillax.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chillax.dto.Goodtype;
import com.chillax.service.GoodTypeService;

@Controller
public class GoodTypeController {
	@Resource
	private GoodTypeService goodTypeService;
	
	@RequestMapping("/selectAllGoodType")
	@ResponseBody
	public List<Goodtype> selectAllGoodType(HttpServletRequest request, HttpServletResponse response) {
		List<Goodtype> goodTypeList = goodTypeService.selectAllGoodType();
		return goodTypeList;
	}

}
