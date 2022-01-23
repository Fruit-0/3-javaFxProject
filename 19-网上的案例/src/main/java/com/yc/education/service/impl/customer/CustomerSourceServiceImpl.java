package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.CustomerSourceMapper;
import com.yc.education.model.customer.CustomerSource;
import com.yc.education.service.customer.ICustomerSourceService;
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
public class CustomerSourceServiceImpl extends BaseService<CustomerSource> implements ICustomerSourceService{

    @Autowired
    CustomerSourceMapper mapper;

    @Override
    public List<CustomerSource> listCustomerSourceAll() {
        try{
            return mapper.listCustomerSourceAll();
        }catch (Exception e){
            return null;
        }
    }
}
