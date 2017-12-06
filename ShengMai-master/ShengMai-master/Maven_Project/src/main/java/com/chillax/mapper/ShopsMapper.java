package com.chillax.mapper;

import com.chillax.dto.Shops;
import com.chillax.dto.ShopsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopsMapper {
	
	/*����
	 * �������̵�������ѯ��Ӧ�Ķ��ֲ�Ʒ
	 * */
	Shops selectOneToManyByShopid(Integer id);
	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    long countByExample(ShopsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int deleteByExample(ShopsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int insert(Shops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int insertSelective(Shops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    List<Shops> selectByExample(ShopsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    Shops selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Shops record, @Param("example") ShopsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Shops record, @Param("example") ShopsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Shops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shops
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Shops record);
}