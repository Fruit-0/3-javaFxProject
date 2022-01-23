package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.AreaMapper;
import com.yc.education.mapper.customer.CustomerDetailInfoMapper;
import com.yc.education.model.customer.Area;
import com.yc.education.model.customer.CustomerDetailInfo;
import com.yc.education.service.customer.IAreaService;
import com.yc.education.service.customer.ICustomerDetailInfoService;
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
public class CustomerDetailInfoServiceImpl extends BaseService<CustomerDetailInfo> implements ICustomerDetailInfoService{

    @Autowired
    CustomerDetailInfoMapper mapper;


    @Override
    public CustomerDetailInfo getCustomerDetailInfoByCustomerId(long customerid) {
        try {
            return mapper.getCustomerDetailInfoByCustomerId(customerid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
