package com.chillax.service;

import java.util.List;

import com.chillax.dto.Goods;


public interface GoodsService {
/*��ѯ���в�Ʒ*/	
	List<Goods> selectAllGoods();
/*��ID��ѯ��Ʒ��Ϣ*/	
	Goods selectGoodById(Integer id);
/*�����µĲ�Ʒ*/
	int insertGood(Goods good);
/*�ύ�����̻���Ϣ*/
	int updateGood(Goods good);
}
