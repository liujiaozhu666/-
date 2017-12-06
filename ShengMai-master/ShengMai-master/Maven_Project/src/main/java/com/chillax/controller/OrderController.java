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
	
//�ύ�����涩����δ����״̬�ģ�
	@RequestMapping("/saveOrderDetail")
	@ResponseBody
	public int saveOrderDetail(HttpServletRequest request,HttpServletResponse response){
		
//�ж϶�����ʶ��ȡ����������֧���ɹ�
		String payflag = request.getParameter("payflag");
		
//��������������
		String stringOrderDetail = request.getParameter("orderDetail");//����
		String addressid = request.getParameter("addressid");//��ַid---
		/*long orderdetailid =  System.currentTimeMillis();*///����id---
		long orderdetailid = Long.parseLong(request.getParameter("orderdetailid"));
//������json��ת��json����
		JSONObject jsonOrderDetail = JSON.parseObject(stringOrderDetail);
//��ȡ�����е�����
		BigDecimal orderdetailsum = jsonOrderDetail.getBigDecimal("orderdetailsum");//�����ܽ��---
		String wxid = jsonOrderDetail.getString("wxid");//�û�΢��id---
		String orderdetailshopname = jsonOrderDetail.getString("orderdetailshopname");//��������---
		String orderdetailshoptel = jsonOrderDetail.getString("orderdetailshoptel");//���̵绰---
//��ȡ����json�����е�foods����
		String stringFoods = jsonOrderDetail.getString("thisorderfoods");
		JSONArray arrayFoods = JSON.parseArray(stringFoods);
//��ȡ����json�����е�thisordercart����
		String stringCart = jsonOrderDetail.getString("thisordercart");
		JSONObject jsonCart = JSON.parseObject(stringCart);
//��ʼ�������ﳵcart��ѡ�еĲ�Ʒ��ȡ���������µ�jsonArray
//��ʽ���£�{"0":2,"2":3,"5":4},key��Ӧ�����arrayFoods�����е��±꣬����Ӧ�Ĳ�Ʒ��value����Ӧ��Ʒѡ��������
		JSONArray jsonArrayFoodCount = new JSONArray();//���ﳵ��Ʒ--����json����
		JSONObject jsonCartList = jsonCart.getJSONObject("list");
		Set<String> keySetOfCartList = jsonCartList.keySet();
		for (String key : keySetOfCartList) {
			int value = (int) jsonCartList.get(key);//��ȡ���ﳵ��key����Ʒ�±꣬��Ӧvalue����Ʒѡ������
			int intKeyOfCartList = Integer.parseInt(key);
			for(int i = 0;i<arrayFoods.size();i++){
				if(i == intKeyOfCartList){
					JSONObject jsonFood = JSON.parseObject(arrayFoods.get(i).toString());//��cart��key���Ӧfoods�����±��foodjson����
					String foodName = jsonFood.getString("name");
					BigDecimal price = jsonFood.getBigDecimal("price");
					JSONObject jsonFoodCount = new JSONObject();//��Ʒ--����json����
					jsonFoodCount.put("foodName", foodName);
					jsonFoodCount.put("foodCount", value);
					jsonFoodCount.put("foodSum", price.multiply(new BigDecimal(value)));
					jsonArrayFoodCount.add(jsonFoodCount);
				}
			}
		}
		System.out.println("------------���ﳵjson����");
		System.out.println(jsonArrayFoodCount);
		String orderDetailContent = jsonArrayFoodCount.toString();//�����в�Ʒѡ��json��---
		
//����orderdetail�洢����
		Orderdetail orderDetail = new Orderdetail();
		orderDetail.setOrderdetailid(orderdetailid);
		orderDetail.setOrderdetailsum(orderdetailsum);
		orderDetail.setOrderdetailcontent(orderDetailContent);
		orderDetail.setOrderdetailflag(1);
		orderDetail.setWxid(wxid);
		orderDetail.setShopname(orderdetailshopname);
		orderDetail.setShopphone(orderdetailshoptel);
		if("payok".equals(payflag)){
			orderDetail.setOrderdetailstate("���������");
		}else if("paycancel".equals(payflag)){
			orderDetail.setOrderdetailstate("�ȴ�֧��");
		}
		orderDetail.setOrdertime(new Date(System.currentTimeMillis()));
		orderDetail.setAddressid(Integer.parseInt(addressid));
		
		return orderDetailService.addOrderDetail(orderDetail);
	}
	
	
	//����΢��openid��ȡ���ж���
	@RequestMapping("/getAllOrderDetailByWxid")
	@ResponseBody
	public List<Orderdetail> getAllOrderDetailByWxid(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		return orderDetailService.getAllOrderDetailByWxid(wxid);
	}
	
	/*ȡ������*/
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public int cancelOrder(HttpServletRequest request,HttpServletResponse response){
		
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		
		return orderDetailService.cancelOrder(wxid, orderDetailId);
	}
	
	/*ȡ��������ٸ���״ֵ̬��Ϊ���������*/
	@RequestMapping("/successOrder")
	@ResponseBody
	public int successOrder(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		return orderDetailService.successOrder(wxid, orderDetailId);
	}
	
	/*ȡ��������ٸ�����ٴ�ȡ���Ķ���Ϊ��ȡ��*/
	@RequestMapping("/invalidOrder")
	@ResponseBody
	public int invalidOrder(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		String orderdetailid = request.getParameter("orderdetailid");
		long orderDetailId = Long.valueOf(orderdetailid);
		return orderDetailService.invalidOrder(wxid, orderDetailId);
	}
}
