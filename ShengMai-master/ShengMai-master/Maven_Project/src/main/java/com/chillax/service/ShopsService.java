package com.chillax.service;

import java.util.List;

import com.chillax.dto.Shops;

public interface ShopsService {
/*��ѯ�����̻�*/
	List<Shops> getAllShops();
/*�����µ��̻�*/
	int insertShop(Shops shop);
/*��ת�����̻�ҳ��*/
	Shops selectById(Integer id);
/*�ύ�����̻���Ϣ*/
	int updateShop(Shops shop);
	
/*�������̵�shopid��ѯ�����Ĳ�Ʒ*/
	Shops selectOneToManyByShopid(Integer shopid);
}
