package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.IndustryMapper;
import com.yc.education.model.customer.Industry;
import com.yc.education.service.customer.IIndustryService;
import com.yc.education.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 14:11 2018-08-24
 */
@Service
public class IndustryServiceImpl extends BaseService<Industry> implements IIndustryService{


    @Autowired
    IndustryMapper mapper;

    @Override
    public List<Industry> listIndustryAll() {
        try {
            return mapper.listIndustryAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
