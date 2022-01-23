package com.yc.education.mapper.customer;


import com.yc.education.model.customer.CustomerDataMaintainBasic;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CustomerDataMaintainBasicMapper extends MyMapper<CustomerDataMaintainBasic> {

    /**
     * 查询所有
     * @param leaderPeople
     * @return
     */
    List<CustomerDataMaintainBasic> listCustomerDataMaintainBasic(@Param("leaderPeople") String leaderPeople);

    /**
     * 通过客户资料id查询基本信息
     * @return
     */
    CustomerDataMaintainBasic getCustomerDataMaintainBasicByMaintainId(@Param("maintainId") long maintainId);

    /**
     * 通过客户资料id删除基本资料
     * @param maintainId
     * @return
     */
    int deleteCustomerDataMaintainBasicByMaintainId(@Param("maintainId") long maintainId);
}