package com.chillax.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chillax.dto.Address;
import com.chillax.dto.AddressExample;
import com.chillax.dto.AddressExample.Criteria;
import com.chillax.mapper.AddressMapper;
import com.chillax.service.AddressService;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Resource
	private AddressMapper addressMapper;
	
	@Override
	public int deleteSingleAddress(int id){
		Address address = new Address();
		address.setId(id);
		address.setAddressflag("2");
		int updateByPrimaryKeySelective = addressMapper.updateByPrimaryKeySelective(address);
		return updateByPrimaryKeySelective;
	}
	
	@Override
	public Address getDefaultAddress(String wxid) {
		AddressExample addressExample = new AddressExample();
		Criteria createCriteria = addressExample.createCriteria();
		createCriteria.andAddressdefaultEqualTo("y");
		createCriteria.andAddressflagEqualTo("1");
		createCriteria.andWxidEqualTo(wxid);
		List<Address> addressList = addressMapper.selectByExample(addressExample);
		if(null!=addressList && addressList.size()>=1){
			Address address = addressList.get(0);
			return address;
		}else{
			return null;
		}
	}

	@Override
	public List<Address> getAllAddress(String wxid) {
		AddressExample addressExample = new AddressExample();
		Criteria createCriteria = addressExample.createCriteria();
		createCriteria.andAddressflagEqualTo("1");
		createCriteria.andWxidEqualTo(wxid);
		addressExample.setOrderByClause("addressdefault desc");
		List<Address> allAddress = addressMapper.selectByExample(addressExample);
		return allAddress;
	}

	@Override
	public int updateDefaultAddress(Address address) {
		int updatecount = addressMapper.updateAddressDefault(address);
		return updatecount;
	}

	@Override
	public int addAddress(Address address) {
		int addcount = addressMapper.insert(address);
		addressMapper.updateAddressDefault(address);
		return addcount;
	}

	@Override
	public int updateAddress(Address address) {
		int updatecount = addressMapper.updateByPrimaryKey(address);
		return updatecount;
	}

	@Override
	public Address getAddressByOpenidAndAddressid(String wxid, int addressid) {
		AddressExample addressExample = new AddressExample();
		Criteria createCriteria = addressExample.createCriteria();
		createCriteria.andAddressidEqualTo(addressid);
		createCriteria.andWxidEqualTo(wxid);
		List<Address> selectByExample = addressMapper.selectByExample(addressExample);
		return selectByExample.get(0);
	}

}
