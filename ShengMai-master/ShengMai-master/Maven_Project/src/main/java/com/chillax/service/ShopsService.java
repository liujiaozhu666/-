package com.chillax.service;

import java.util.List;

import com.chillax.dto.Shops;

public interface ShopsService {
/*查询所有商户*/
	List<Shops> getAllShops();
/*加入新的商户*/
	int insertShop(Shops shop);
/*跳转更新商户页面*/
	Shops selectById(Integer id);
/*提交更新商户信息*/
	int updateShop(Shops shop);
	
/*根据商铺的shopid查询囊括的菜品*/
	Shops selectOneToManyByShopid(Integer shopid);
}
