package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.CustomerProductionCategoryMapper;
import com.yc.education.model.customer.CustomerProductionCategory;
import com.yc.education.service.customer.ICustomerProductionCategoryService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 10:33 2018-08-24
 */
@Service
public class CustomerProductionCategoryServiceImpl extends BaseService<CustomerProductionCategory> implements ICustomerProductionCategoryService {

    @Autowired
    CustomerProductionCategoryMapper mapper;


    @Override
    public List<CustomerProductionCategory> listCustomerProductionCategoryAll() {
        try{
            return mapper.listCustomerProductionCategoryAll();
        }catch (Exception e){
            return null;
        }
    }
}
