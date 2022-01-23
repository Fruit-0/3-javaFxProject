package com.yc.education.mapper.customer;

import com.yc.education.model.customer.Area;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AreaMapper extends MyMapper<Area> {

    /**
     * 查询所有的省份
     * @return
     */
    @Select("SELECT * from area where parentid = -1")
    public List<Area> listProvice();

    /**
     * 根据地区查询
     * @param areaid  省、市的地区Id
     * @return
     */
    @Select("SELECT * from area where parentid = #{areaid}")
    public List<Area> listAreaById(@Param("areaid") String areaid);
}