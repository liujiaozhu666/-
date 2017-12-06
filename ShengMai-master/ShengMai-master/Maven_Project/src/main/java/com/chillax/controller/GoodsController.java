package com.chillax.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chillax.dto.Goods;
import com.chillax.service.GoodsService;

@Controller
public class GoodsController {
	@Resource
	private GoodsService goodsService;
	
	/*根据ID查询单个商户*/	
	@RequestMapping("/selectByGoodId")
	public String selectByGoodId(@RequestParam("goodId") String goodId,Model model){
		Goods good = goodsService.selectGoodById(Integer.parseInt(goodId));
		model.addAttribute("good", good);
		return "updategood";
	}	

	/* 查询所有菜品 */
	@RequestMapping("/selectAllGoods")
	public String selectAllGoods(HttpServletRequest request, HttpServletResponse response) {
		List<Goods> goodsList = goodsService.selectAllGoods();
		JSONArray goods = JSON.parseArray((JSON.toJSONString(goodsList)));
		request.setAttribute("goods", goods);
		return "goodslist";
	}
	/*用于给微信返回数据的查询所有菜品的方法*/
	@RequestMapping("/selectAllGood")
	@ResponseBody
	public List<Goods> selectAllGood(HttpServletRequest request, HttpServletResponse response) {
		List<Goods> goodsList = goodsService.selectAllGoods();
		/*JSONArray goods = JSON.parseArray((JSON.toJSONString(goodsList)));
		request.setAttribute("goods", goods);*/
		return goodsList;
	}

	/* 加入新的菜品 */
	@RequestMapping("/insertGood")
	public String insertGood(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		Goods good = new Goods();
		String goodName = request.getParameter("name");
		String goodSold = request.getParameter("sold");
		String goodPrice = request.getParameter("price");

		good.setName(goodName);
		good.setPrice(new BigDecimal(goodPrice));
		good.setSold(Integer.parseInt(goodSold));
		good.setGoodflag(1);
		/* 上传菜品的图片 */
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
		if (cmr.isMultipart(request)) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			Iterator<String> files = mRequest.getFileNames();
			while (files.hasNext()) {
				MultipartFile mFile = mRequest.getFile(files.next());
				String fileName = mFile.getOriginalFilename();
				String path = "D:/WeiXinApp/TEST/TakeawayDemo/imgs/goods/" + fileName;
				File localFile = new File(path);
				mFile.transferTo(localFile);
				/* 将菜品的图片的虚拟路径存入goods对象中，以便存入数据库作将来访问使用 */
				String picPath = "/imgs/goods/" + fileName;
				good.setPic(picPath);
			}

		}

		int insertGood = goodsService.insertGood(good);
		return "forward:/selectAllGoods.do";

	}

	/* 更新商户信息 */
	@RequestMapping("/updateGood")
	public String updateShops(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		Integer goodId = Integer.parseInt(request.getParameter("goodId"));
		Goods good = goodsService.selectGoodById(goodId);

		String goodName = request.getParameter("name");
		String goodSold = request.getParameter("sold");
		String goodPrice = request.getParameter("price");

		good.setName(goodName);
		good.setSold(Integer.parseInt(goodSold));
		good.setPrice(new BigDecimal(goodPrice));

		/* 上传商户logo图片 */
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
		if (cmr.isMultipart(request)) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> files = mRequest.getFileNames();
			while (files.hasNext()) {
				MultipartFile mFile = mRequest.getFile(files.next());
				if (mFile != null) {
					String fileName = mFile.getOriginalFilename();
					String path = "D:/WeiXinApp/TEST/TakeawayDemo/imgs/goods/" + fileName;
					File localFile = new File(path);
					if(!localFile.exists()){
						mFile.transferTo(localFile);
					/* 将logo图片的虚拟路径存入shops对象中，以便存入数据库作将来访问使用 */
						String picPath = "/imgs/goods/" + fileName;
						good.setPic(picPath);
					}else{
						String picPath = "/imgs/goods/" + fileName;
						good.setPic(picPath);
					}
					
				}
			}
		}

		int insertGood = goodsService.updateGood(good);

		return "forward:/selectAllGoods.do";
	}

}
