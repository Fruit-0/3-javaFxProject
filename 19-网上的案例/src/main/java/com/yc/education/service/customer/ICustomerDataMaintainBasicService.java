package com.yc.education.service.customer;


import com.yc.education.model.customer.CustomerDataMaintainBasic;
import com.yc.education.service.IService;


import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 15:38 2018-08-31
 */
public interface ICustomerDataMaintainBasicService extends IService<CustomerDataMaintainBasic>{
    /**
     * 查询所有
     * @param leaderPeople
     * @return
     */
    List<CustomerDataMaintainBasic> listCustomerDataMaintainBasic( String leaderPeople,int page,int rows);

    /**
     * 通过客户资料id查询基本信息
     * @return
     */
    CustomerDataMaintainBasic getCustomerDataMaintainBasicByMaintainId(long maintainId);

    /**
     * 通过客户资料id删除基本资料
     * @param maintainId
     * @return
     */
    int deleteCustomerDataMaintainBasicByMaintainId(long maintainId);
}
