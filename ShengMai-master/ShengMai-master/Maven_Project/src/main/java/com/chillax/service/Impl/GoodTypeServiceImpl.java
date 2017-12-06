package com.chillax.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chillax.dto.Goodtype;
import com.chillax.dto.GoodtypeExample;
import com.chillax.mapper.GoodtypeMapper;
import com.chillax.service.GoodTypeService;

@Service("goodTypeService")
public class GoodTypeServiceImpl implements GoodTypeService {
	@Resource
	private GoodtypeMapper goodtypeMapper;

	@Override
	public List<Goodtype> selectAllGoodType() {
		GoodtypeExample goodtypeExample = new GoodtypeExample();
		GoodtypeExample.Criteria criteria = goodtypeExample.createCriteria();
		criteria.andGoodtypeflagEqualTo(1);
		return goodtypeMapper.selectByExample(goodtypeExample);
	}

}
