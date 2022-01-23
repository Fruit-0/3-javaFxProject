package com.yc.education.mapper.customer;


import com.yc.education.model.customer.CustomerProductionCategory;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CustomerProductionCategoryMapper extends MyMapper<CustomerProductionCategory> {

    /**
     * 查询所有的生产类别
     * @return
     */
    @Select("select * from customer_production_category order by sort desc,addtime desc")
    List<CustomerProductionCategory> listCustomerProductionCategoryAll();
}