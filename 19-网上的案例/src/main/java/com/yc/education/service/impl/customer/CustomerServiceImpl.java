package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.CustomerMapper;
import com.yc.education.model.customer.Customer;
import com.yc.education.service.customer.ICustomerService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BlueSky
 * @Date: 2018/8/20 11:24
 */
@Service
public class CustomerServiceImpl extends BaseService<Customer> implements ICustomerService {

    @Autowired
    private CustomerMapper mapper;

    @Override
    public List<Customer> listExistCustomer(int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listExistCustomer();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Customer> listCustomerAll(int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return  mapper.listCustomerAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Customer getCustomer(String code) {
        try {
            return  mapper.getCustomer(code);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Customer getLastCustomer() {
        try {
            return  mapper.getLastCustomer();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Customer getFirstCustomer() {
        try {
            return  mapper.getFirstCustomer();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Customer getCustomerByPage(int page, int rows) {
        try {
            return  mapper.getCustomerByPage(page,rows);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getCustomerCount() {
        try {
            return  mapper.getCustomerCount();
        }catch (Exception e){
            return 0;
        }
    }
}
