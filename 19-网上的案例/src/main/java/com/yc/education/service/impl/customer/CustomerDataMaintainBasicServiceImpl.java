package com.yc.education.service.impl.customer;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.customer.CustomerDataMaintainBasicMapper;
import com.yc.education.mapper.customer.CustomerDataMaintainMapper;
import com.yc.education.model.customer.CustomerDataMaintain;
import com.yc.education.model.customer.CustomerDataMaintainBasic;
import com.yc.education.service.customer.ICustomerDataMaintainBasicService;
import com.yc.education.service.customer.ICustomerDataMaintainService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 18:50 2018-08-31
 */
@Service
public class CustomerDataMaintainBasicServiceImpl extends BaseService<CustomerDataMaintainBasic> implements ICustomerDataMaintainBasicService {

    @Autowired
    CustomerDataMaintainBasicMapper mapper;


    @Override
    public List<CustomerDataMaintainBasic> listCustomerDataMaintainBasic(String leaderPeople, int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return mapper.listCustomerDataMaintainBasic(leaderPeople);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDataMaintainBasic getCustomerDataMaintainBasicByMaintainId(long maintainId) {
        try {
            return mapper.getCustomerDataMaintainBasicByMaintainId(maintainId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteCustomerDataMaintainBasicByMaintainId(long maintainId) {
        try {
            return mapper.deleteCustomerDataMaintainBasicByMaintainId(maintainId);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
