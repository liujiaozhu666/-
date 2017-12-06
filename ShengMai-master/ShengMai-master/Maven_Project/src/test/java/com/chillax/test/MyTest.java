package com.chillax.test;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.chillax.controller.ShopsController;
import com.chillax.dto.Address;
import com.chillax.dto.AddressExample;
import com.chillax.dto.AddressExample.Criteria;
import com.chillax.dto.Shops;
import com.chillax.mapper.AddressMapper;
import com.chillax.mapper.ShopsMapper;

public class MyTest extends TestBase{
	
	@Resource
	private AddressMapper addressMapper;
	
	@Resource
	private ShopsMapper shopsMapper;
	
	@Resource
	private ShopsController shopsController;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		AddressExample ae = new AddressExample();
		Criteria createCriteria = ae.createCriteria();
		createCriteria.andAddressdefaultEqualTo("y");
		createCriteria.andAddressflagEqualTo("1");
		createCriteria.andWxidEqualTo("obf3x0Jql7RwEztNIKlkTYMBsZ7");
		List addressList = addressMapper.selectByExample(ae);
		System.out.println(JSON.toJSON(addressList));
	}
	
	@Test
	public void updateTest(){
		Address address = new Address();
		address.setId(1);
		address.setAddressid(3);
		address.setWxid("001");
		addressMapper.updateAddressDefault(address);
	}
	
	@Test
	public void testOneShopToManyGood(){
		int shopId = 1;
		HttpServletResponse httpServletResponse = null;
		Shops shop = shopsController.selectGoodsOfShopByShopid(shopId,  httpServletResponse);
		System.out.println(JSON.toJSON(shop));
	}
	
	@Test
	public void testPrint(){
		System.out.println((System.currentTimeMillis()));
	}
	

}
