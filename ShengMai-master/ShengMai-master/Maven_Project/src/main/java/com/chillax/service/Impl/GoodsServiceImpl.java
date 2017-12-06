package com.chillax.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.chillax.dto.Goods;
import com.chillax.dto.GoodsExample;
import com.chillax.mapper.GoodsMapper;
import com.chillax.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;

	@Override
	public List<Goods> selectAllGoods() {
		GoodsExample goodsExample = new GoodsExample();
		GoodsExample.Criteria criteria = goodsExample.createCriteria();
		criteria.andGoodflagEqualTo(1);
		return goodsMapper.selectByExample(goodsExample);
	}

	@Override
	public Goods selectGoodById(Integer id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertGood(Goods good) {
		return goodsMapper.insert(good);
	}

	@Override
	public int updateGood(Goods good) {
		return goodsMapper.updateByPrimaryKeySelective(good);
	}

}
