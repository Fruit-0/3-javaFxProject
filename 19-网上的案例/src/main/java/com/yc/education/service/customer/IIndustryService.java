package com.yc.education.service.customer;

import com.yc.education.model.customer.Industry;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 14:09 2018-08-24
 */
public interface IIndustryService extends IService<Industry>{

    /**
     * 查询所有的行业
     * @return
     */
    List<Industry> listIndustryAll();
}
