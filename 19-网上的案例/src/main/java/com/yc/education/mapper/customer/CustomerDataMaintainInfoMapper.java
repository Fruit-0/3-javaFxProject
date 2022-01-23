package com.yc.education.mapper.customer;


import com.yc.education.model.customer.CustomerDataMaintainInfo;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CustomerDataMaintainInfoMapper extends MyMapper<CustomerDataMaintainInfo> {

    /**
     * 查询所有
     * @return
     */
    List<CustomerDataMaintainInfo> listCustomerDataMaintainInfo();

    /**
     * 通过客户资料id查询详情
     * @return
     */
    CustomerDataMaintainInfo getCustomerDataMaintainInfoByMaintainId(@Param("maintainId") long maintainId);

    /**
     * 通过客户资料id删除详情
     * @param maintainId
     * @return
     */
    int deleteCustomerDataMaintainInfoByMaintainId(@Param("maintainId") long maintainId);
}