package com.chillax.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chillax.dto.Shops;
import com.chillax.service.ShopsService;

@Controller
public class ShopsController {
	

	@Resource
	private ShopsService shopsService;
	
/*根据ID查询单个商户*/	
	@RequestMapping("/selectByShopId")
	public String selectByShopId(@RequestParam("shopId") String shopId,Model model){
		System.out.println("shopId:"+shopId);
		Shops shop = shopsService.selectById(Integer.parseInt(shopId));
		model.addAttribute("shop", shop);
		return "updateshops";
	}
	
/*查询所有商户*/	
	@RequestMapping("/getAllShops")
	public String getAllShops(HttpServletRequest request, HttpServletResponse response) {
		
		List<Shops> shopsList = shopsService.getAllShops();
		System.out.println(JSON.toJSONString(shopsList));
		JSONArray shops = JSON.parseArray((JSON.toJSONString(shopsList)));
		request.setAttribute("shops",shops);
		
		return "shopslist";
	}
/*用于给微信返回数据的查询所有商户的方法*/	
	@RequestMapping("/getAllShop")
	@ResponseBody
	public List<Shops> getAllShop(HttpServletRequest request, HttpServletResponse response) {
		
		List<Shops> shopsList = shopsService.getAllShops();
		/*System.out.println(JSON.toJSONString(shopsList));
		JSONArray shops = JSON.parseArray((JSON.toJSONString(shopsList)));
		request.setAttribute("shops",shops);*/
		
		return shopsList;
	}
	
/*加入新的商户*/	
	@RequestMapping("/insertShops")
	public String insertShop(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		Shops shop = new Shops();
		
		String shopName = request.getParameter("name");
		String shopDistance = request.getParameter("distance");
		String shopSales = request.getParameter("sales");
		String shopActivity = request.getParameter("activity");
		
		shop.setName(shopName);
		shop.setDistance(Double.parseDouble(shopDistance));
		shop.setActivity(shopActivity);
		shop.setSales(Integer.parseInt(shopSales));
		shop.setShopflag(1);
		
		/*上传商户logo图片*/
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
		if (cmr.isMultipart(request)) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> files = mRequest.getFileNames();
			while (files.hasNext()) {
				MultipartFile mFile = mRequest.getFile(files.next());
				if (mFile != null) {
					String fileName = mFile.getOriginalFilename();
					String path = "D:/WeiXinApp/TEST/TakeawayDemo/imgs/shop/" + fileName;
					File localFile = new File(path);
					mFile.transferTo(localFile);
				/*将logo图片的虚拟路径存入shops对象中，以便存入数据库作将来访问使用*/
					String logoPath="/imgs/shop/"+fileName;
					shop.setLogo(logoPath);
				}
			}
		}
		
		int insertShop = shopsService.insertShop(shop);
		
		return "forward:/getAllShops.do";
	}
	
/*更新商户信息*/
	@RequestMapping("/updateShop")
	public String updateShops(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		Integer shopId = Integer.parseInt(request.getParameter("shopId"));
		Shops shop = shopsService.selectById(shopId);
		
		String shopName = request.getParameter("name");
		String shopDistance = request.getParameter("distance");
		String shopSales = request.getParameter("sales");
		String shopActivity = request.getParameter("activity");
		
		shop.setName(shopName);
		shop.setDistance(Double.parseDouble(shopDistance));
		shop.setActivity(shopActivity);
		shop.setSales(Integer.parseInt(shopSales));
		
		/*上传商户logo图片*/
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
		if (cmr.isMultipart(request)) {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> files = mRequest.getFileNames();
			while (files.hasNext()) {
				MultipartFile mFile = mRequest.getFile(files.next());
				if (mFile != null) {
					String fileName = mFile.getOriginalFilename();
					String path = "D:/WeiXinApp/TEST/TakeawayDemo/imgs/shop/" + fileName;
					File localFile = new File(path);
					if(!localFile.exists()){
						mFile.transferTo(localFile);
					/*将logo图片的虚拟路径存入shops对象中，以便存入数据库作将来访问使用*/
						String logoPath="/imgs/shop/"+fileName;
						shop.setLogo(logoPath);
					}else{
						String logoPath="/imgs/shop/"+fileName;
						shop.setLogo(logoPath);
					}
				
					
				}
			}
		}
		
		int insertShop = shopsService.updateShop(shop);
		
		return "forward:/getAllShops.do";
	}
	
	/*查询商铺底下的囊括的菜品,restful风格*/
	@RequestMapping(value="/selectGoodsOfShopByShopid/{shopid}",method=RequestMethod.GET)
	@ResponseBody
	public Shops selectGoodsOfShopByShopid(@PathVariable("shopid") Integer shopid,HttpServletResponse respnse){
		
		return shopsService.selectOneToManyByShopid(shopid);
	}
}
