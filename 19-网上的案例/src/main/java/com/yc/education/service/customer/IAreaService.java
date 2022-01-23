package com.yc.education.service.customer;

import com.yc.education.model.customer.Area;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 15:16 2018-08-24
 */
public interface IAreaService extends IService<Area>{

    /**
     * 查询所有的省份
     * @return
     */
    public List<Area> listProvice();

    /**
     * 根据地区查询
     * @param areaid  省、市的地区Id
     * @return
     */
    public List<Area> listAreaById(String areaid);
}
