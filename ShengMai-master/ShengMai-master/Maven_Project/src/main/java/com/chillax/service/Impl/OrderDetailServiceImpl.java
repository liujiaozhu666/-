package com.chillax.service.Impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chillax.dto.Orderdetail;
import com.chillax.dto.OrderdetailExample;
import com.chillax.dto.OrderdetailExample.Criteria;
import com.chillax.mapper.OrderdetailMapper;
import com.chillax.service.OrderDetailService;

@Service("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {

	@Resource
	private OrderdetailMapper orderdetailMapper;
	
	@Override
	public int addOrderDetail(Orderdetail orderdetail) {
		int addCount = orderdetailMapper.insert(orderdetail);
		return addCount;
	}

	@Override
	public List<Orderdetail> getAllOrderDetailByWxid(String wxid) {
		OrderdetailExample orderDetailExample = new OrderdetailExample();
		Criteria createCriteria = orderDetailExample.createCriteria();
		createCriteria.andOrderdetailflagEqualTo(1);
		createCriteria.andWxidEqualTo(wxid);
		orderDetailExample.setOrderByClause("ordertime desc");
		List<Orderdetail> orderdetailList = orderdetailMapper.selectByExample(orderDetailExample);
		return orderdetailList;
	}

	@Override
	public int cancelOrder(String wxid, long orderDetailId) {
		
		OrderdetailExample orderDetailExample = new OrderdetailExample();
		Criteria createCriteria = orderDetailExample.createCriteria();
		createCriteria.andWxidEqualTo(wxid);
		createCriteria.andOrderdetailidEqualTo(orderDetailId);
		Orderdetail orderDetail = new Orderdetail();
		orderDetail.setOrderdetailstate("订单已取消");
		
		return orderdetailMapper.updateByExampleSelective(orderDetail, orderDetailExample);
	}

	@Override
	public int successOrder(String wxid, long orderDetailId) {
		OrderdetailExample orderDetailExample = new OrderdetailExample();
		Criteria createCriteria = orderDetailExample.createCriteria();
		createCriteria.andWxidEqualTo(wxid);
		createCriteria.andOrderdetailidEqualTo(orderDetailId);
		
		Orderdetail orderDetail = new Orderdetail();
		orderDetail.setOrderdetailstate("订单已完成");
		orderDetail.setOrdertime(new Date(System.currentTimeMillis()));
		
		return orderdetailMapper.updateByExampleSelective(orderDetail, orderDetailExample);
	}
	
	public int invalidOrder(String wxid, long orderDetailId) {
		OrderdetailExample orderDetailExample = new OrderdetailExample();
		Criteria createCriteria = orderDetailExample.createCriteria();
		createCriteria.andWxidEqualTo(wxid);
		createCriteria.andOrderdetailidEqualTo(orderDetailId);
		
		Orderdetail orderDetail = new Orderdetail();
		orderDetail.setOrderdetailstate("订单已取消");
		orderDetail.setOrdertime(new Date(System.currentTimeMillis()));
		
		return orderdetailMapper.updateByExampleSelective(orderDetail, orderDetailExample);
	}

}
