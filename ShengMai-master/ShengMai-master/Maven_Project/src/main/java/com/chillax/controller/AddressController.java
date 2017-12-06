package com.chillax.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chillax.dto.Address;
import com.chillax.service.AddressService;

@Controller
public class AddressController {
	
	@Resource
	private AddressService addressService;
	
	//获取用户设置的默认地址，并返回给小程序页面
	@RequestMapping("/getDefaultAddress")
	@ResponseBody
	public Address getDefaultAddress(HttpServletRequest request,HttpServletResponse response){
		System.out.println("用户id是"+request.getParameter("wxid"));
		String wxid = request.getParameter("wxid");
		return addressService.getDefaultAddress(wxid);
	}
	
	//获取用户存储的所有地址，返回给小程序的address各文件
	@RequestMapping("/getAllAddress")
	@ResponseBody
	public List<Address> getAllAddress(HttpServletRequest request,HttpServletResponse response){
		System.out.println("用户id是"+request.getParameter("wxid"));
		String wxid = request.getParameter("wxid");
		return addressService.getAllAddress(wxid);
	}
	
	/*在小程序地址列表页面改变默认地址*/
	@RequestMapping(value="/updateDefaultAddress",method=RequestMethod.GET,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public int updateDefaultAddress(HttpServletRequest request,HttpServletResponse response){
		String defaultAddress = request.getParameter("dftaddress");
		Address address = JSON.parseObject(defaultAddress, Address.class);
		return addressService.updateDefaultAddress(address);
	}
	
	/*小程序添加收获地址form提交至此*/
	@RequestMapping(value="/addAddress",method=RequestMethod.GET,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public int addAddress(HttpServletRequest request,HttpServletResponse response){
		String addAddress =request.getParameter("addAddress");
		Address address = JSON.parseObject(addAddress, Address.class);
		address.setAddressdefault("y");
		address.setAddressflag("1");
		address.setAddressid((int)System.currentTimeMillis());
		System.out.println(addAddress);
		System.out.println(address.getAddressname());
		return addressService.addAddress(address);
	}
	
	/*小程序添加地址界面，更新地址时，按主键更新收获地址*/
	@RequestMapping(value="/updateAddress",method=RequestMethod.GET,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public int updateAddress(HttpServletRequest request,HttpServletResponse response){
		String updateAddress = request.getParameter("updateAddress");
		Address address = JSON.parseObject(updateAddress, Address.class);
		return addressService.updateAddress(address);
	}
	
	/*小程序地址页面，删除地址，restFul风格url，直接在url中写入要删除的地址主键，controller接收入参传递后，按主键删除*/
	@RequestMapping(value="/deleteSingleAddress/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public int deleteSingleAddress(@PathVariable("id") Integer id,HttpServletResponse response){
		System.out.println("地址id："+id);
		return addressService.deleteSingleAddress(id);
	}
	
	/*根据用户id和addressid查询地址*/
	@RequestMapping("/getAddressByOpenidAndAddressid")
	@ResponseBody
	public Address getAddressByOpenidAndAddressid(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		int addressid = Integer.parseInt(request.getParameter("addressid"));
		
		return addressService.getAddressByOpenidAndAddressid(wxid, addressid);
	}

}
