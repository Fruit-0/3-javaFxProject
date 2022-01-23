package com.yc.education.service.customer;

import com.yc.education.model.customer.CustomerDataMaintain;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 15:38 2018-08-31
 */
public interface ICustomerDataMaintainService extends IService<CustomerDataMaintain>{
    /**
     * 查询所有
     * @return
     */
    List<CustomerDataMaintain> listCustomerDataMaintain(String name,int page,int rows);

    /**
     * 通过客户资料id删除所有信息
     * @param customerid
     * @return
     */
    int deleteCustomerDataMaintainByCustomerId(long customerid);

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
    CustomerDataMaintain getCustomerDataMaintainByPage(int page,int rows);

    /**
     * 统计客户资料数量
     * @return
     */
    int getCustomerDataMaintainCount();
}
