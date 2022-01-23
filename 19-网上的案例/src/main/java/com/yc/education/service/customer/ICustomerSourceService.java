package com.yc.education.service.customer;


import com.yc.education.model.customer.CustomerSource;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @Author: BlueSky
 * @Date: 2018/8/20 11:23
 */
public interface ICustomerSourceService extends IService<CustomerSource>{

    /**
     * 查询客户
     * @return
     */
    List<CustomerSource> listCustomerSourceAll();
}
