package com.yc.education.mapper.customer;


import com.yc.education.model.customer.CustomerDataMaintain;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CustomerDataMaintainMapper extends MyMapper<CustomerDataMaintain> {

    /**
     * 查询所有
     * @return
     */
    List<CustomerDataMaintain> listCustomerDataMaintain(@Param("name") String name);

    /**
     * 通过客户资料id删除所有信息
     * @param customerid
     * @return
     */
    int deleteCustomerDataMaintainByCustomerId(@Param("customerid") long customerid);

    /**
     * 最后一条数据
     * @return
     */
    CustomerDataMaintain getLastCustomerDataMaintain();

    /**
     * 第一条数据
     * @return
     */
    CustomerDataMaintain getFirstCustomerDataMaintain();

    /**
     * 获取上下页数据
     * @return
     */
    CustomerDataMaintain getCustomerDataMaintainByPage(@Param("page")int page, @Param("rows")int rows);

    /**
     * 统计客户资料数量
     * @return
     */
    int getCustomerDataMaintainCount();
}