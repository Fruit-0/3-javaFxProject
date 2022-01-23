package com.yc.education.service.customer;


import com.yc.education.model.customer.CustomerProductionCategory;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @Author: BlueSky
 * @Date: 2018/8/20 11:23
 */
public interface ICustomerProductionCategoryService extends IService<CustomerProductionCategory>{

    /**
     * 查询所有生产类别
     * @return
     */
    List<CustomerProductionCategory> listCustomerProductionCategoryAll();
}
