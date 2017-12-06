package com.chillax.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chillax.dto.Orderdetail;
import com.chillax.service.OrderDetailService;

@Controller
public class OrderController {
	
	@Resource
	private OrderDetailService orderDetailService;
	
//提交并保存订单（未付款状态的）
	@RequestMapping("/saveOrderDetail")
	@ResponseBody
	public int saveOrderDetail(HttpServletRequest request,HttpServletResponse response){
		
//判断订单标识：取消订单还是支付成功
		String payflag = request.getParameter("payflag");
		
//订单中所有数据
		String stringOrderDetail = request.getParameter("orderDetail");//订单
		String addressid = request.getParameter("addressid");//地址id---
		/*long orderdetailid =  System.currentTimeMillis();*///订单id---
		long orderdetailid = Long.parseLong(request.getParameter("orderdetailid"));
//将订单json串转成json对象
		JSONObject jsonOrderDetail = JSON.parseObject(stringOrderDetail);
//获取订单中的数据
		BigDecimal orderdetailsum = jsonOrderDetail.getBigDecimal("orderdetailsum");//订单总金额---
		String wxid = jsonOrderDetail.getString("wxid");//用户微信id---
		String orderdetailshopname = jsonOrderDetail.getString("orderdetailshopname");//店铺名称---
		String orderdetailshoptel = jsonOrderDetail.getString("orderdetailshoptel");//店铺电话---
//获取订单json对象中的foods数组
		String stringFoods = jsonOrderDetail.getString("thisorderfoods");
		JSONArray arrayFoods = JSON.parseArray(stringFoods);
//获取订单json对象中的thisordercart对象
		String stringCart = jsonOrderDetail.getString("thisordercart");
		JSONObject jsonCart = JSON.parseObject(stringCart);
//开始，将购物车cart中选中的菜品提取出来放入新的jsonArray
//格式如下：{"0":2,"2":3,"5":4},key对应上面的arrayFoods数组中的下标，所对应的菜品，value是相应菜品选购的数量
		JSONArray jsonArrayFoodCount = new JSONArray();//购物车菜品--数量json数组
		JSONObject jsonCartList = jsonCart.getJSONObject("list");
		Set<String> keySetOfCartList = jsonCartList.keySet();
		for (String key : keySetOfCartList) {
			int value = (int) jsonCartList.get(key);//获取购物车中key即菜品下标，对应value即菜品选购数量
			int intKeyOfCartList = Integer.parseInt(key);
			for(int i = 0;i<arrayFoods.size();i++){
				if(i == intKeyOfCartList){
					JSONObject jsonFood = JSON.parseObject(arrayFoods.get(i).toString());//与cart中key相对应foods数组下标的foodjson对象
					String foodName = jsonFood.getString("name");
					BigDecimal price = jsonFood.getBigDecimal("price");
					JSONObject jsonFoodCount = new JSONObject();//菜品--数量json对象
					jsonFoodCount.put("foodName", foodName);
					jsonFoodCount.put("foodCount", value);
					jsonFoodCount.put("foodSum", price.multiply(new BigDecimal(value)));
					jsonArrayFoodCount.add(jsonFoodCount);
				}
			}
		}
		System.out.println("------------购物车json数组");
		System.out.println(jsonArrayFoodCount);
		String orderDetailContent = jsonArrayFoodCount.toString();//订单中菜品选购json串---
		
//创建orderdetail存储数据
		Orderdetail orderDetail = new Orderdetail();
		orderDetail.setOrderdetailid(orderdetailid);
		orderDetail.setOrderdetailsum(orderdetailsum);
		orderDetail.setOrderdetailcontent(orderDetailContent);
		orderDetail.setOrderdetailflag(1);
		orderDetail.setWxid(wxid);
		orderDetail.setShopname(orderdetailshopname);
		orderDetail.setShopphone(orderdetailshoptel);
		if("payok".equals(payflag)){
			orderDetail.setOrderdetailstate("订单已完成");
		}else if("paycancel".equals(payflag)){
			orderDetail.setOrderdetailstate("等待支付");
		}
		orderDetail.setOrdertime(new Date(System.currentTimeMillis()));
		orderDetail.setAddressid(Integer.parseInt(addressid));
		
		return orderDetailService.addOrderDetail(orderDetail);
	}
	
	
	//根据微信openid获取所有订单
	@RequestMapping("/getAllOrderDetailByWxid")
	@ResponseBody
	public List<Orderdetail> getAllOrderDetailByWxid(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		return orderDetailService.getAllOrderDetailByWxid(wxid);
	}
	
	/*取消订单*/
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public int cancelOrder(HttpServletRequest request,HttpServletResponse response){
		
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		
		return orderDetailService.cancelOrder(wxid, orderDetailId);
	}
	
	/*取消付款后，再付款状态值改为订单已完成*/
	@RequestMapping("/successOrder")
	@ResponseBody
	public int successOrder(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		return orderDetailService.successOrder(wxid, orderDetailId);
	}
	
	/*取消付款后，再付款并且再次取消改订单为已取消*/
	@RequestMapping("/invalidOrder")
	@ResponseBody
	public int invalidOrder(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		return orderDetailService.invalidOrder(wxid, orderDetailId);
	}
}
