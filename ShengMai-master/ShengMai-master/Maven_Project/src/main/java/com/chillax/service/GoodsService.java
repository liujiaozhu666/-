package com.chillax.service;

import java.util.List;

import com.chillax.dto.Goods;


public interface GoodsService {
/*查询所有菜品*/	
	List<Goods> selectAllGoods();
/*按ID查询菜品信息*/	
	Goods selectGoodById(Integer id);
/*加入新的菜品*/
	int insertGood(Goods good);
/*提交更新商户信息*/
	int updateGood(Goods good);
}
