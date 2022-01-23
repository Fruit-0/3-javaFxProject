package com.yc.education.mapper.customer;

import com.yc.education.model.customer.CustomerSource;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CustomerSourceMapper extends MyMapper<CustomerSource> {

    /**
     *  查询所有的客户来源
     * @return
     */
    @Select("select * from customer_source order by sort desc,addtime desc")
    public List<CustomerSource> listCustomerSourceAll();
}