package com.chillax.service;

import java.util.List;

import com.chillax.dto.Address;

public interface AddressService {

	Address getDefaultAddress(String wxid);
	
	List<Address> getAllAddress(String wxid);
	
	int updateDefaultAddress(Address address);
	
	int addAddress(Address address);
	
	int updateAddress(Address address);
	
	int deleteSingleAddress(int id);
	
	Address getAddressByOpenidAndAddressid(String wxid,int addressid);
}
