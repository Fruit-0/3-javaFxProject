package com.yc.education.service.impl.customer;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.customer.CustomerDataMaintainMapper;
import com.yc.education.model.customer.CustomerDataMaintain;
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
public class CustomerDataMaintainServiceImpl extends BaseService<CustomerDataMaintain> implements ICustomerDataMaintainService {

    @Autowired
    CustomerDataMaintainMapper mapper;

    @Override
    public List<CustomerDataMaintain> listCustomerDataMaintain(String name, int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return mapper.listCustomerDataMaintain(name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteCustomerDataMaintainByCustomerId(long customerid) {
        try {
            return mapper.deleteCustomerDataMaintainByCustomerId(customerid);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public CustomerDataMaintain getLastCustomerDataMaintain() {
        try {
            return mapper.getLastCustomerDataMaintain();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDataMaintain getFirstCustomerDataMaintain() {
        try {
            return mapper.getFirstCustomerDataMaintain();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDataMaintain getCustomerDataMaintainByPage(int page, int rows) {
        try {
            return mapper.getCustomerDataMaintainByPage(page,rows);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCustomerDataMaintainCount() {
        try {
            return mapper.getCustomerDataMaintainCount();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
