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
	
	//��ȡ�û����õ�Ĭ�ϵ�ַ�������ظ�С����ҳ��
	@RequestMapping("/getDefaultAddress")
	@ResponseBody
	public Address getDefaultAddress(HttpServletRequest request,HttpServletResponse response){
		System.out.println("�û�id��"+request.getParameter("wxid"));
		String wxid = request.getParameter("wxid");
		return addressService.getDefaultAddress(wxid);
	}
	
	//��ȡ�û��洢�����е�ַ�����ظ�С�����address���ļ�
	@RequestMapping("/getAllAddress")
	@ResponseBody
	public List<Address> getAllAddress(HttpServletRequest request,HttpServletResponse response){
		System.out.println("�û�id��"+request.getParameter("wxid"));
		String wxid = request.getParameter("wxid");
		return addressService.getAllAddress(wxid);
	}
	
	/*��С�����ַ�б�ҳ��ı�Ĭ�ϵ�ַ*/
	@RequestMapping(value="/updateDefaultAddress",method=RequestMethod.GET,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public int updateDefaultAddress(HttpServletRequest request,HttpServletResponse response){
		String defaultAddress = request.getParameter("dftaddress");
		Address address = JSON.parseObject(defaultAddress, Address.class);
		return addressService.updateDefaultAddress(address);
	}
	
	/*С��������ջ��ַform�ύ����*/
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
	
	/*С������ӵ�ַ���棬���µ�ַʱ�������������ջ��ַ*/
	@RequestMapping(value="/updateAddress",method=RequestMethod.GET,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public int updateAddress(HttpServletRequest request,HttpServletResponse response){
		String updateAddress = request.getParameter("updateAddress");
		Address address = JSON.parseObject(updateAddress, Address.class);
		return addressService.updateAddress(address);
	}
	
	/*С�����ַҳ�棬ɾ����ַ��restFul���url��ֱ����url��д��Ҫɾ���ĵ�ַ������controller������δ��ݺ󣬰�����ɾ��*/
	@RequestMapping(value="/deleteSingleAddress/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public int deleteSingleAddress(@PathVariable("id") Integer id,HttpServletResponse response){
		System.out.println("��ַid��"+id);
		return addressService.deleteSingleAddress(id);
	}
	
	/*�����û�id��addressid��ѯ��ַ*/
	@RequestMapping("/getAddressByOpenidAndAddressid")
	@ResponseBody
	public Address getAddressByOpenidAndAddressid(HttpServletRequest request,HttpServletResponse response){
		String wxid = request.getParameter("wxid");
		int addressid = Integer.parseInt(request.getParameter("addressid"));
		
		return addressService.getAddressByOpenidAndAddressid(wxid, addressid);
	}

}
