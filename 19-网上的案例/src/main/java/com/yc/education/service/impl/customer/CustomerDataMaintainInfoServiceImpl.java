package com.yc.education.service.impl.customer;

import com.github.pagehelper.PageHelper;
import com.yc.education.mapper.customer.CustomerDataMaintainInfoMapper;
import com.yc.education.mapper.customer.CustomerDataMaintainMapper;
import com.yc.education.model.customer.CustomerDataMaintain;
import com.yc.education.model.customer.CustomerDataMaintainInfo;
import com.yc.education.service.customer.ICustomerDataMaintainInfoService;
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
public class CustomerDataMaintainInfoServiceImpl extends BaseService<CustomerDataMaintainInfo> implements ICustomerDataMaintainInfoService {

    @Autowired
    CustomerDataMaintainInfoMapper mapper;



    @Override
    public List<CustomerDataMaintainInfo> listCustomerDataMaintainInfo(int page, int rows) {
        try {
            PageHelper.startPage(page,rows);
            return mapper.listCustomerDataMaintainInfo();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDataMaintainInfo getCustomerDataMaintainInfoByMaintainId(long maintainId) {
        try {
            return mapper.getCustomerDataMaintainInfoByMaintainId(maintainId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteCustomerDataMaintainInfoByMaintainId(long maintainId) {
        try {
            return mapper.deleteCustomerDataMaintainInfoByMaintainId(maintainId);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
