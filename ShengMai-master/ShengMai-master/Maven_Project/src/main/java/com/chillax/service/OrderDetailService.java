package com.chillax.service;

import java.util.List;

import com.chillax.dto.Orderdetail;

public interface OrderDetailService {

	int addOrderDetail(Orderdetail orderdetail);
	
	List<Orderdetail> getAllOrderDetailByWxid(String wxid);
	
	int cancelOrder(String wxid,long orderDetailId);
	
	int successOrder(String wxid,long orderDetailId);
	
	int invalidOrder(String wxid,long orderDetailId);
}
