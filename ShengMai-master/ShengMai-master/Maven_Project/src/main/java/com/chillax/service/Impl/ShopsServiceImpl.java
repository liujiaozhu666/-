package com.chillax.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chillax.dto.Shops;
import com.chillax.dto.ShopsExample;
import com.chillax.mapper.ShopsMapper;

@Service("shopsService")
public class ShopsServiceImpl implements com.chillax.service.ShopsService {

	@Resource
	private ShopsMapper shopsMapper;
	
	@Override
	public List<Shops> getAllShops() {
		ShopsExample shopsExample = new ShopsExample();
		ShopsExample.Criteria criteria = shopsExample.createCriteria();
		criteria.andShopflagEqualTo(1);
		List<Shops> shopsList=shopsMapper.selectByExample(shopsExample);
		return shopsList;
	}

	@Override
	public int insertShop(Shops shop) {
		
		return shopsMapper.insert(shop);
	}

	@Override
	public Shops selectById(Integer id) {
		Shops shop = shopsMapper.selectByPrimaryKey(id);
		return shop;
	}

	@Override
	public int updateShop(Shops shop) {
		
		return shopsMapper.updateByPrimaryKeySelective(shop);
	}

/*�������̵�shopid��ѯ�����Ĳ�Ʒ*/
	@Override
	public Shops selectOneToManyByShopid(Integer shopid) {
		return shopsMapper.selectOneToManyByShopid(shopid);
	}

}
