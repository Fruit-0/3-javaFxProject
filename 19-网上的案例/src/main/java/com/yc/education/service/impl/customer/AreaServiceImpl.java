package com.yc.education.service.impl.customer;

import com.yc.education.mapper.customer.AreaMapper;
import com.yc.education.model.customer.Area;
import com.yc.education.service.customer.IAreaService;
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
public class AreaServiceImpl extends BaseService<Area> implements IAreaService{

    @Autowired
    AreaMapper mapper;


    @Override
    public List<Area> listProvice() {
        try {
            return mapper.listProvice();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Area> listAreaById(String areaid) {
        try {
            return mapper.listAreaById(areaid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
